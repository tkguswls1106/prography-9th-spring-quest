package com.sahyunjin.prographyspringquest.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageResponseDto {

    private Integer totalElements;
    private Integer totalPages;
    private List<UserResponseDto> userList;
}
