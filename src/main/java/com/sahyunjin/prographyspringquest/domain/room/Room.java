package com.sahyunjin.prographyspringquest.domain.room;

import com.sahyunjin.prographyspringquest.domain.DefaultRoomEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor

@Table(name = "room")
@Entity
public class Room extends DefaultRoomEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(name="host_id")
    private Integer hostId;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    private RoomStatus status;


    @Builder(builderClassName = "RoomSaveBuilder", builderMethodName = "RoomSaveBuilder")
    public Room(Integer hostId, RoomType roomType, String title) {
        // 방 저장 용도의 빌더
        this.title = title;
        this.hostId = hostId;
        this.roomType = roomType;
        this.status = RoomStatus.WAIT;
    }


    // 수정(업데이트) 기능
    public void updateRoomStatus(RoomStatus status) {
        this.status = status;
    }
}
