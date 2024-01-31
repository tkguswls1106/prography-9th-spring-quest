package com.sahyunjin.prographyspringquest.controller;

import com.sahyunjin.prographyspringquest.dto.room.RoomSaveRequestDto;
import com.sahyunjin.prographyspringquest.response.ApiResponse;
import com.sahyunjin.prographyspringquest.response.ResponseCode;
import com.sahyunjin.prographyspringquest.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;


    @PostMapping("/room")
    public ResponseEntity createRoom(@RequestBody RoomSaveRequestDto roomSaveRequestDto) {
        roomService.createRoom(roomSaveRequestDto);
        return ApiResponse.toResponseEntity(ResponseCode.SUCCESS);
    }
}
