package com.sahyunjin.prographyspringquest.dto.room;

import com.sahyunjin.prographyspringquest.domain.room.Room;
import com.sahyunjin.prographyspringquest.domain.room.RoomType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomSaveRequestDto {

    private Integer userId;
    private String roomType;
    private String title;


    public Room toEntity() {
        RoomType newRoomType;
        if(roomType.equals("SINGLE")) {
            newRoomType = RoomType.SINGLE;
        }
        else if(roomType.equals("DOUBLE")) {
            newRoomType = RoomType.DOUBLE;
        }
        else {  // 입력값이 잘못된 경우 예외처리 시킴.
            throw new RuntimeException("ERROR - roomType 입력값 오류");
        }

        return Room.RoomSaveBuilder()
                .title(title)
                .hostId(userId)
                .roomType(newRoomType)
                .build();
    }
}
