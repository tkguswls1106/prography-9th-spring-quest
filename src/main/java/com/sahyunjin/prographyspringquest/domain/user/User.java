package com.sahyunjin.prographyspringquest.domain.user;

import com.sahyunjin.prographyspringquest.domain.DefaultUserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor

@Table(name = "member")  // h2 database 에서는 user 테이블명이 예약어로 겹치기때문에, member로 이름을 명명해주었음.
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

    @Builder(builderClassName = "UserTestBuilder", builderMethodName = "UserTestBuilder")
    public User(Integer id) {
        // 사용자 테스트 용도의 빌더
        this.id = id;
        this.fakerId = 1;
        this.name = "name";
        this.email = "email";
        this.status = Status.ACTIVE;
    }
}
