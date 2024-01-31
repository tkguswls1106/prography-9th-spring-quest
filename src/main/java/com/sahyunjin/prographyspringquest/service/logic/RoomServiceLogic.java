package com.sahyunjin.prographyspringquest.service.logic;

import com.sahyunjin.prographyspringquest.domain.room.Room;
import com.sahyunjin.prographyspringquest.domain.room.RoomJpaRepository;
import com.sahyunjin.prographyspringquest.domain.user.Status;
import com.sahyunjin.prographyspringquest.domain.user.User;
import com.sahyunjin.prographyspringquest.domain.user.UserJpaRepository;
import com.sahyunjin.prographyspringquest.domain.userroom.UserRoom;
import com.sahyunjin.prographyspringquest.domain.userroom.UserRoomJpaRepository;
import com.sahyunjin.prographyspringquest.dto.room.RoomFindResponseDto;
import com.sahyunjin.prographyspringquest.dto.room.RoomPageResponseDto;
import com.sahyunjin.prographyspringquest.dto.room.RoomResponseDto;
import com.sahyunjin.prographyspringquest.dto.room.RoomSaveRequestDto;
import com.sahyunjin.prographyspringquest.dto.userroom.UserRoomSaveRequestDto;
import com.sahyunjin.prographyspringquest.response.exeption.BadRequestErrorException;
import com.sahyunjin.prographyspringquest.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomServiceLogic implements RoomService {

    private final RoomJpaRepository roomJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final UserRoomJpaRepository userRoomJpaRepository;


    @Transactional
    @Override
    public void createRoom(RoomSaveRequestDto roomSaveRequestDto) {

        User user = userJpaRepository.findById(roomSaveRequestDto.getUserId()).orElseThrow(
                ()->new BadRequestErrorException());
        if(user.getStatus() != Status.ACTIVE) {  // 방을 생성하려고 하는 user(userId)의 상태가 활성(ACTIVE)상태일 때만, 방을 생성 가능.
            throw new BadRequestErrorException();
        }

        boolean isExistsUserRoom = userRoomJpaRepository.existsByUserId(user.getId());
        if(isExistsUserRoom) {  // 방을 생성하려고 하는 user(userId)가 현재 참여한 방이 있다면, 방을 생성할 수 없음.
            throw new BadRequestErrorException();
        }

        Room room = roomSaveRequestDto.toEntity();
        roomJpaRepository.save(room);  // 1. 먼저 Room 정보를 저장한후에,

        UserRoomSaveRequestDto userRoomSaveRequestDto = new UserRoomSaveRequestDto(room.getId(), user.getId());
        UserRoom userRoom = userRoomSaveRequestDto.toEntity();
        userRoomJpaRepository.save(userRoom);  // 2. 다음 순서로 UserRoom 정보를 저장.
    }

    @Transactional(readOnly = true)
    @Override
    public RoomPageResponseDto findRooms(Integer size, Integer page) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());  // id 기준으로 오름차순 정렬
        Page<Room> rooms = roomJpaRepository.findAll(pageable);
        Page<RoomResponseDto> roomResponseDtos = rooms.map(RoomResponseDto::new);  // entity를 dto로 변환

        RoomPageResponseDto roomPageResponseDto = new RoomPageResponseDto(Long.valueOf(roomResponseDtos.getTotalElements()).intValue(), roomResponseDtos.getTotalPages(), roomResponseDtos.getContent());

        return roomPageResponseDto;
    }

    @Transactional(readOnly = true)
    @Override
    public RoomFindResponseDto findOneRoom(Integer roomId) {

        Room room = roomJpaRepository.findById(roomId).orElseThrow(
                ()->new BadRequestErrorException());  // 존재하지 않는 id에 대한 요청이라면 201 응답을 반환.
        RoomFindResponseDto roomFindResponseDto = new RoomFindResponseDto(room);

        return roomFindResponseDto;
    }
}
