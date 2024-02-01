package com.sahyunjin.prographyspringquest.domain.userroom;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor

@Table(name = "user_room")
@Entity
public class UserRoom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="room_id")
    private Integer roomId;

    @Column(name="user_id")
    private Integer userId;

    @Enumerated(EnumType.STRING)
    private Team team;


    @Builder(builderClassName = "UserRoomSaveBuilder", builderMethodName = "UserRoomSaveBuilder")
    public UserRoom(Integer roomId, Integer userId, Team team) {
        // 사용자&방 저장 용도의 빌더
        this.roomId = roomId;
        this.userId = userId;
        this.team = team;
    }

    @Builder(builderClassName = "UserRoomTestBuilder", builderMethodName = "UserRoomTestBuilder")
    public UserRoom(Integer id) {
        // 사용자_방 테스트 용도의 빌더
        this.id = id;
        this.roomId = 1;
        this.userId = 1;
        this.team = Team.RED;
    }


    // 수정(업데이트) 기능
    public void updateTeam(Team team) {
        this.team = team;
    }
}
