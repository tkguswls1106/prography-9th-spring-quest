package com.sahyunjin.prographyspringquest.dto.room;

import com.sahyunjin.prographyspringquest.domain.room.Room;
import com.sahyunjin.prographyspringquest.domain.room.RoomStatus;
import com.sahyunjin.prographyspringquest.domain.room.RoomType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomResponseDto {

    private Integer id;
    private String title;
    private Integer hostId;
    private RoomType roomType;
    private RoomStatus status;

    public RoomResponseDto(Room entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.hostId = entity.getHostId();
        this.roomType = entity.getRoomType();
        this.status = entity.getStatus();
    }
}
