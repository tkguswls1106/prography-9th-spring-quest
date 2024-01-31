package com.sahyunjin.prographyspringquest.service.logic;

import com.sahyunjin.prographyspringquest.domain.room.Room;
import com.sahyunjin.prographyspringquest.domain.room.RoomJpaRepository;
import com.sahyunjin.prographyspringquest.domain.room.RoomStatus;
import com.sahyunjin.prographyspringquest.domain.room.RoomType;
import com.sahyunjin.prographyspringquest.domain.user.Status;
import com.sahyunjin.prographyspringquest.domain.user.User;
import com.sahyunjin.prographyspringquest.domain.user.UserJpaRepository;
import com.sahyunjin.prographyspringquest.domain.userroom.Team;
import com.sahyunjin.prographyspringquest.domain.userroom.UserRoom;
import com.sahyunjin.prographyspringquest.domain.userroom.UserRoomJpaRepository;
import com.sahyunjin.prographyspringquest.dto.userroom.UserRoomAttentionRequestDto;
import com.sahyunjin.prographyspringquest.response.exeption.BadRequestErrorException;
import com.sahyunjin.prographyspringquest.service.UserRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserRoomServiceLogic implements UserRoomService {

    private final UserRoomJpaRepository userRoomJpaRepository;
    private final RoomJpaRepository roomJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final TransactionTemplate transactionTemplate;


    @Transactional
    @Override
    public void attentionRoom(Integer roomId, UserRoomAttentionRequestDto userRoomAttentionRequestDto) {

        // 201 응답 조건 1,5
        Room room = roomJpaRepository.findById(roomId).orElseThrow(
                ()->new BadRequestErrorException());
        if(room.getStatus() != RoomStatus.WAIT) {
            // 대기(WAIT) 상태인 방에만 참가가 가능.
            // (애초에 FINISH면 여기서 막히므로, 호스트는 무조건 UserRoom 테이블에 데이터가 존재함. 단, 차후 Team 변경으로 방장이 RED팀이 아닐수가 있음을 주의할것!)
            throw new BadRequestErrorException();
        }

        // 201 응답 조건 2,5
        User user = userJpaRepository.findById(userRoomAttentionRequestDto.getUserId()).orElseThrow(
                ()->new BadRequestErrorException());
        if(user.getStatus() != Status.ACTIVE) {  // 유저(userId)가 활성(ACTIVE) 상태일 때만, 방에 참가가 가능.
            throw new BadRequestErrorException();
        }

        // 201 응답 조건 3
        boolean isExistsUserRoom = userRoomJpaRepository.existsByUserId(user.getId());
        if(isExistsUserRoom) {  // 유저(userId)가 현재 참여한 방이 없을때만, 방에 참가가 가능.
            throw new BadRequestErrorException();
        }

        int redTeamCount = 0;
        List<UserRoom> userRoomList = userRoomJpaRepository.findAllByRoomId(roomId);
        for(int i=0; i<userRoomList.size(); i++) {
            if(userRoomList.get(i).getTeam() == Team.RED) redTeamCount++;
        }

        // 201 응답 조건 4
        int waitUsersCount = userRoomList.size();
        Team attentionTeam;
        if(room.getRoomType() == RoomType.SINGLE) {  // SINGLE(단식) 2인게임인 경우라면
            if(!(waitUsersCount < 2)) {  // 참가하고자 하는 방(roomId)의 정원이 미달일 때만, 참가가 가능.
                throw new BadRequestErrorException();
            }
            else {
                if(redTeamCount != 1) attentionTeam = Team.RED;
                else attentionTeam = Team.BLUE;
            }
        }
        else {  // DOUBLE(복식) 4인게임인 경우라면
            if(!(waitUsersCount < 4)) {  // 참가하고자 하는 방(roomId)의 정원이 미달일 때만, 참가가 가능.
                throw new BadRequestErrorException();
            }
            else {
                if(redTeamCount < 2) attentionTeam = Team.RED;
                else attentionTeam = Team.BLUE;
            }
        }

        UserRoom userRoom = UserRoom.UserRoomSaveBuilder()
                .roomId(roomId)
                .userId(user.getId())
                .team(attentionTeam)
                .build();
        userRoomJpaRepository.save(userRoom);
    }

    @Transactional
    @Override
    public void outRoom(Integer roomId, UserRoomAttentionRequestDto userRoomAttentionRequestDto) {  // UserRoomAttentionRequestDto 클래스를 재사용하겠음.

        Room room = roomJpaRepository.findById(roomId).orElseThrow(
                ()->new BadRequestErrorException());

        boolean isExistsUser = userJpaRepository.existsById(userRoomAttentionRequestDto.getUserId());
        if(!isExistsUser) throw new BadRequestErrorException();

        UserRoom userRoom = userRoomJpaRepository.findByUserIdAndRoomId(userRoomAttentionRequestDto.getUserId(), roomId).orElseThrow(
                ()->new BadRequestErrorException());  // 유저(userId)가 현재 해당 방(roomId)에 참가한 상태일 때만, 나가기가 가능.

        if(room.getStatus() != RoomStatus.WAIT) {  // 이미 시작(PROGRESS) 상태인 방이거나 끝난(FINISH) 상태의 방은 나갈수없음.
            throw new BadRequestErrorException();
        }

        if(room.getHostId().equals(userRoomAttentionRequestDto.getUserId())) {  // 방을 나가려는 사람이 호스트라면
            userRoomJpaRepository.deleteAllByRoomId(roomId);  // 방에 있던 모든 사람도 해당 방에서 나가게 함.
            room.updateRoomStatus(RoomStatus.FINISH);  // 해당 방은 끝난(FINISH) 상태가 됨. (soft-delete 방식처럼 실제로 방 데이터를 삭제하진 않겠음.)
        }
        else {  // 방을 나가려는 사람이 호스트가 아니라면
            userRoomJpaRepository.delete(userRoom);  // 해당 사람만 방에서 나가게 함.
        }
    }

    @Transactional
    @Override
    public void gameStart(Integer roomId, UserRoomAttentionRequestDto userRoomAttentionRequestDto) {  // UserRoomAttentionRequestDto 클래스를 재사용하겠음.

        Room room = roomJpaRepository.findById(roomId).orElseThrow(
                ()->new BadRequestErrorException());
        if(room.getStatus() != RoomStatus.WAIT) {  // 현재 방의 상태가 대기(WAIT) 상태일 때만 시작 가능.
            throw new BadRequestErrorException();
        }

        boolean isExistsUser = userJpaRepository.existsById(userRoomAttentionRequestDto.getUserId());
        boolean isExistsUserRoom = userRoomJpaRepository.existsByUserIdAndRoomId(userRoomAttentionRequestDto.getUserId(), roomId);
        if((!isExistsUser) || (!isExistsUserRoom)) throw new BadRequestErrorException();

        if(!(room.getHostId().equals(userRoomAttentionRequestDto.getUserId()))) {  // 호스트인 유저만 게임 시작 가능.
            throw new BadRequestErrorException();
        }

        // 방 정원이 방의 타입에 맞게 모두 꽉 찬 상태에서만 게임 시작 가능.
        List<UserRoom> userRoomList = userRoomJpaRepository.findAllByRoomId(roomId);
        if(room.getRoomType() == RoomType.SINGLE) {  // SINGLE(단식) 2인게임인 경우라면
            if(userRoomList.size() != 2) throw new BadRequestErrorException();
        }
        else {  // DOUBLE(복식) 4인게임인 경우라면
            if(userRoomList.size() != 4) throw new BadRequestErrorException();
        }

        room.updateRoomStatus(RoomStatus.PROGRESS);  // 방의 상태를 진행중(PROGRESS) 상태로 변경.

        // 게임시작이 된 방은 1분 뒤 종료(FINISH) 상태로 변경됨. 그리고 UserRoom에서 제거시킴. Room에는 FINISH 상태로 유지.
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    userRoomJpaRepository.deleteAllByRoomId(roomId);
                    Room delayedRoom = roomJpaRepository.findById(roomId).orElseThrow(
                            ()->new BadRequestErrorException());
                    delayedRoom.updateRoomStatus(RoomStatus.FINISH);
                }
            });
        }, 1, TimeUnit.MINUTES);
        executorService.shutdown();
    }

    @Transactional
    @Override
    public void changeTeam(Integer roomId, UserRoomAttentionRequestDto userRoomAttentionRequestDto) {  // UserRoomAttentionRequestDto 클래스를 재사용하겠음.

        Room room = roomJpaRepository.findById(roomId).orElseThrow(
                ()->new BadRequestErrorException());
        if(room.getStatus() != RoomStatus.WAIT) {  // 현재 방의 상태가 대기(WAIT) 상태일 때만 팀을 변경 가능.
            throw new BadRequestErrorException();
        }

        boolean isExistsUser = userJpaRepository.existsById(userRoomAttentionRequestDto.getUserId());
        if(!isExistsUser) throw new BadRequestErrorException();

        UserRoom userRoom = userRoomJpaRepository.findByUserIdAndRoomId(userRoomAttentionRequestDto.getUserId(), roomId).orElseThrow(
                ()->new BadRequestErrorException());  // 유저(userId)가 현재 해당 방(roomId)에 참가한 상태일 때만, 나가기가 가능.

        int redTeamCount = 0, blueTeamCount = 0;
        List<UserRoom> userRoomList = userRoomJpaRepository.findAllByRoomId(roomId);
        for(int i=0; i<userRoomList.size(); i++) {
            if(userRoomList.get(i).getTeam() == Team.RED) redTeamCount++;
            else blueTeamCount++;
        }

        // 변경되려는 팀의 인원이 이미 해당 방 정원의 절반과 같다면 팀이 변경되지 않고 201 응답을 반환.
        int waitUsersCount = userRoomList.size();
        Team nowTeam = userRoom.getTeam();
        if(room.getRoomType() == RoomType.SINGLE) {  // SINGLE(단식) 2인게임인 경우라면
            if(nowTeam == Team.RED && blueTeamCount == 1) throw new BadRequestErrorException();
            else if(nowTeam == Team.BLUE && redTeamCount == 1) throw new BadRequestErrorException();
            else {
                if(nowTeam == Team.RED) userRoom.updateTeam(Team.BLUE);
                else userRoom.updateTeam(Team.RED);
            }
        }
        else {  // DOUBLE(복식) 4인게임인 경우라면
            if(nowTeam == Team.RED && blueTeamCount == 2) throw new BadRequestErrorException();
            else if(nowTeam == Team.BLUE && redTeamCount == 2) throw new BadRequestErrorException();
            else {
                if(nowTeam == Team.RED) userRoom.updateTeam(Team.BLUE);
                else userRoom.updateTeam(Team.RED);
            }
        }
    }
}
