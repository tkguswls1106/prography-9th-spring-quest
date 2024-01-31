package com.sahyunjin.prographyspringquest.dto.room;

import com.sahyunjin.prographyspringquest.domain.room.Room;
import com.sahyunjin.prographyspringquest.domain.room.RoomStatus;
import com.sahyunjin.prographyspringquest.domain.room.RoomType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomFindResponseDto {

    private Integer id;
    private String title;
    private Integer hostId;
    private RoomType roomType;
    private RoomStatus status;
    private String createdAt;
    private String updatedAt;

    public RoomFindResponseDto(Room entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.hostId = entity.getHostId();
        this.roomType = entity.getRoomType();
        this.status = entity.getStatus();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }
}
