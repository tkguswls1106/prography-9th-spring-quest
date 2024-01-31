package com.sahyunjin.prographyspringquest.dto.user;

import com.sahyunjin.prographyspringquest.domain.user.Status;
import com.sahyunjin.prographyspringquest.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {

    private Integer id;
    private Integer fakerId;
    private String name;
    private String email;
    private Status status;
    private String createdAt;
    private String updatedAt;

    public UserResponseDto(User entity) {
        this.id = entity.getId();
        this.fakerId = entity.getFakerId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.status = entity.getStatus();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }
}
