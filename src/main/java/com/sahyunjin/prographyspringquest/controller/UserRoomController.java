package com.sahyunjin.prographyspringquest.controller;

import com.sahyunjin.prographyspringquest.dto.userroom.UserRoomAttentionRequestDto;
import com.sahyunjin.prographyspringquest.response.ApiResponse;
import com.sahyunjin.prographyspringquest.response.ResponseCode;
import com.sahyunjin.prographyspringquest.service.UserRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserRoomController {

    private final UserRoomService userRoomService;


    @PostMapping("/room/attention/{roomId}")
    public ResponseEntity attentionRoom(@PathVariable Integer roomId, @RequestBody UserRoomAttentionRequestDto userRoomAttentionRequestDto) {
        userRoomService.attentionRoom(roomId, userRoomAttentionRequestDto);
        return ApiResponse.toResponseEntity(ResponseCode.SUCCESS);
    }

    @PostMapping("/room/out/{roomId}")
    public ResponseEntity outRoom(@PathVariable Integer roomId, @RequestBody UserRoomAttentionRequestDto userRoomAttentionRequestDto) {  // UserRoomAttentionRequestDto 클래스를 재사용하겠음.
        userRoomService.outRoom(roomId, userRoomAttentionRequestDto);
        return ApiResponse.toResponseEntity(ResponseCode.SUCCESS);
    }

    @PutMapping("/team/{roomId}")
    public ResponseEntity changeTeam(@PathVariable Integer roomId, @RequestBody UserRoomAttentionRequestDto userRoomAttentionRequestDto) {  // UserRoomAttentionRequestDto 클래스를 재사용하겠음.
        userRoomService.changeTeam(roomId, userRoomAttentionRequestDto);
        return ApiResponse.toResponseEntity(ResponseCode.SUCCESS);
    }
}
