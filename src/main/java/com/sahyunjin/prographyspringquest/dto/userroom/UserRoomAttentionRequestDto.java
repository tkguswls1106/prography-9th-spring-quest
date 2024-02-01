package com.sahyunjin.prographyspringquest.dto.userroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRoomAttentionRequestDto {

    private Integer userId;


    // 테스트 전용 set매소드
    @JsonIgnore
    public void setId(Integer userId) {
        this.userId = userId;
    }
}
