package com.sahyunjin.prographyspringquest.dto.userroom;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRoomAttentionRequestDto {

    private Integer userId;


    // 테스트 전용 set매소드
    public void setId(Integer userId) {
        this.userId = userId;
    }
}
