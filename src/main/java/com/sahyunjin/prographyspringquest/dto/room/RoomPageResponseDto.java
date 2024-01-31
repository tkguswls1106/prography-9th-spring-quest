package com.sahyunjin.prographyspringquest.dto.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomPageResponseDto {

    private Integer totalElements;
    private Integer totalPages;
    private List<RoomResponseDto> roomList;
}
