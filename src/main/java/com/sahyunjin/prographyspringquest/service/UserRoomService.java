package com.sahyunjin.prographyspringquest.service;

import com.sahyunjin.prographyspringquest.dto.userroom.UserRoomAttentionRequestDto;

public interface UserRoomService {

    void attentionRoom(Integer roomId, UserRoomAttentionRequestDto userRoomAttentionRequestDto);
    void outRoom(Integer roomId, UserRoomAttentionRequestDto userRoomAttentionRequestDto);  // UserRoomAttentionRequestDto 클래스를 재사용하겠음.
    void gameStart(Integer roomId, UserRoomAttentionRequestDto userRoomAttentionRequestDto);  // UserRoomAttentionRequestDto 클래스를 재사용하겠음.
    void changeTeam(Integer roomId, UserRoomAttentionRequestDto userRoomAttentionRequestDto);  // UserRoomAttentionRequestDto 클래스를 재사용하겠음.
}
