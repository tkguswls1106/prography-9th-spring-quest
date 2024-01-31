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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoomServiceLogic implements UserRoomService {

    private final UserRoomJpaRepository userRoomJpaRepository;
    private final RoomJpaRepository roomJpaRepository;
    private final UserJpaRepository userJpaRepository;


    @Transactional
    @Override
    public void attentionRoom(Integer roomId, UserRoomAttentionRequestDto userRoomAttentionRequestDto) {

        // 201 응답 조건 1,5
        Room room = roomJpaRepository.findById(roomId).orElseThrow(
                ()->new BadRequestErrorException());
        if(room.getStatus() != RoomStatus.WAIT) {  // 대기(WAIT) 상태인 방에만 참가가 가능. (애초에 FINISH면 여기서 막히므로, 호스트는 무조건 UserRoom 테이블에 데이터가 존재함.)
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
            else attentionTeam = Team.BLUE;  // 어차피 2인게임 이므로, 방장을 제외하고 남은 BLUE팀에 자동 배정.
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
            room.updateRoomStatus(RoomStatus.FINISH);  // 해당 방은 끝난(FINISH) 상태가 됨.
        }
        else {  // 방을 나가려는 사람이 호스트가 아니라면
            userRoomJpaRepository.delete(userRoom);  // 해당 사람만 방에서 나가게 함.
        }


    }

}
