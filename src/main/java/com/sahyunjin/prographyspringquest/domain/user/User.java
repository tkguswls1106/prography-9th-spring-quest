package com.sahyunjin.prographyspringquest.domain.user;

import com.sahyunjin.prographyspringquest.domain.DefaultUserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor

@Table(name = "user")
@Entity
public class User extends DefaultUserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "faker_id")
    private Integer fakerId;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private Status status;


    @Builder(builderClassName = "UserSaveBuilder", builderMethodName = "UserSaveBuilder")
    public User(Integer fakerId, String name, String email, Status status) {
        // 사용자 저장 용도의 빌더
        this.fakerId = fakerId;
        this.name = name;
        this.email = email;
        this.status = status;
    }
}
