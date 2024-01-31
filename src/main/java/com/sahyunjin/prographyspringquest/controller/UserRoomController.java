package com.sahyunjin.prographyspringquest.controller;

import com.sahyunjin.prographyspringquest.dto.userroom.UserRoomAttentionRequestDto;
import com.sahyunjin.prographyspringquest.response.ApiResponse;
import com.sahyunjin.prographyspringquest.response.ResponseCode;
import com.sahyunjin.prographyspringquest.service.UserRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRoomController {

    private final UserRoomService userRoomService;


    @PostMapping("/room/attention/{roomId}")
    public ResponseEntity attentionRoom(@PathVariable Integer roomId, @RequestBody UserRoomAttentionRequestDto userRoomAttentionRequestDto) {
        userRoomService.attentionRoom(roomId, userRoomAttentionRequestDto);
        return ApiResponse.toResponseEntity(ResponseCode.SUCCESS);
    }
}
