package com.sahyunjin.prographyspringquest.dto.faker;

import com.sahyunjin.prographyspringquest.domain.user.Status;
import com.sahyunjin.prographyspringquest.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@NoArgsConstructor
public class FakerDataResponseDto {

    public Integer id;
    public String uuid;
    public String firstname;
    public String lastname;
    public String username;
    public String password;
    public String email;
    public String ip;
    public String macAddress;
    public String website;
    public String image;


    public User toEntity() {
        Status newStatus;
        if(id <= 30) {
            newStatus = Status.ACTIVE;
        }
        else if(id <= 60) {
            newStatus = Status.WAIT;
        }
        else {
            newStatus = Status.NON_ACTIVE;
        }

        return User.UserSaveBuilder()
                .fakerId(id)
                .name(username)
                .email(email)
                .status(newStatus)
                .build();
    }


    // 테스트 전용 set매소드
    public void setId(Integer id) {
        this.id = id;
    }
}
