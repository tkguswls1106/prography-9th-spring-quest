package com.sahyunjin.prographyspringquest.domain.room;

import com.sahyunjin.prographyspringquest.domain.DefaultRoomEntity;
import jakarta.persistence.*;
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

}
