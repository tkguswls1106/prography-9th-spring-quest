package com.sahyunjin.prographyspringquest.service;

import com.sahyunjin.prographyspringquest.dto.userroom.UserRoomAttentionRequestDto;

public interface UserRoomService {

    void attentionRoom(Integer roomId, UserRoomAttentionRequestDto userRoomAttentionRequestDto);
}
