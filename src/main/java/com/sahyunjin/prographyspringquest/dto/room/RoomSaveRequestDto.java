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
            throw new RuntimeException("ERROR - roomType 입력값 오류");  // 500 응답으로 처리하였음. (문제에 명시된 '존재하지 않는 id에 대한 요청'일 경우가 아니기 때문임.)
        }

        return Room.RoomSaveBuilder()
                .title(title)
                .hostId(userId)
                .roomType(newRoomType)
                .build();
    }


    // 테스트 전용 set매소드
    public void setIdAndRoomType(Integer userId, String roomType) {
        this.userId = userId;
        this.roomType = roomType;
    }
}
