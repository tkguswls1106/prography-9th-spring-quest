package com.sahyunjin.prographyspringquest.dto.userroom;

import com.sahyunjin.prographyspringquest.domain.userroom.Team;
import com.sahyunjin.prographyspringquest.domain.userroom.UserRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoomSaveRequestDto {

    private Integer roomId;
    private Integer userId;


    public UserRoom toEntity() {
        Team newTeam = Team.RED;

        return UserRoom.UserRoomSaveBuilder()
                .roomId(roomId)
                .userId(userId)
                .team(newTeam)
                .build();
    }
}
