package com.sahyunjin.prographyspringquest.service.logic;

import com.sahyunjin.prographyspringquest.domain.room.Room;
import com.sahyunjin.prographyspringquest.domain.room.RoomJpaRepository;
import com.sahyunjin.prographyspringquest.domain.user.Status;
import com.sahyunjin.prographyspringquest.domain.user.User;
import com.sahyunjin.prographyspringquest.domain.user.UserJpaRepository;
import com.sahyunjin.prographyspringquest.dto.room.RoomPageResponseDto;
import com.sahyunjin.prographyspringquest.dto.room.RoomResponseDto;
import com.sahyunjin.prographyspringquest.dto.room.RoomSaveRequestDto;
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


    @Transactional
    @Override
    public void createRoom(RoomSaveRequestDto roomSaveRequestDto) {

        User user = userJpaRepository.findById(roomSaveRequestDto.getUserId()).orElseThrow(
                ()->new RuntimeException("ERROR - 해당 userId의 사용자는 존재하지않습니다."));
        if(user.getStatus() != Status.ACTIVE) {  // 방을 생성하려고 하는 user(userId)의 상태가 활성(ACTIVE)상태일 때만, 방을 생성 가능.
            throw new BadRequestErrorException();
        }

        // !!! 방 등등의 이외 초기화 코드는 차후 추가로 작성 예정. 테스트 코드 또한 마찬가지. !!!
//        if(exists 코드) {  // 방을 생성하려고 하는 user(userId)가 현재 참여한 방이 있다면, 방을 생성할 수 없음.
//            throw new BadRequestErrorException();
//        }

        Room entity = roomSaveRequestDto.toEntity();
        roomJpaRepository.save(entity);
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
}
