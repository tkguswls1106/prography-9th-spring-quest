package com.sahyunjin.prographyspringquest.service;

import com.sahyunjin.prographyspringquest.dto.room.RoomFindResponseDto;
import com.sahyunjin.prographyspringquest.dto.room.RoomPageResponseDto;
import com.sahyunjin.prographyspringquest.dto.room.RoomSaveRequestDto;

public interface RoomService {

    void createRoom(RoomSaveRequestDto roomSaveRequestDto);
    RoomPageResponseDto findRooms(Integer size, Integer page);
    RoomFindResponseDto findOneRoom(Integer roomId);
}
