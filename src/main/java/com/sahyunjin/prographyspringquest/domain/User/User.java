package com.sahyunjin.prographyspringquest.domain.User;

import com.sahyunjin.prographyspringquest.domain.DefaultUserEntity;
import jakarta.persistence.*;
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
}
