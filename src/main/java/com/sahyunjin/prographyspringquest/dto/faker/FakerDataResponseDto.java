package com.sahyunjin.prographyspringquest.dto.faker;

import com.sahyunjin.prographyspringquest.domain.user.Status;
import com.sahyunjin.prographyspringquest.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FakerDataResponseDto {

    private Integer id;
    private String uuid;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private String ip;
    private String macAddress;
    private String website;
    private String image;


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
