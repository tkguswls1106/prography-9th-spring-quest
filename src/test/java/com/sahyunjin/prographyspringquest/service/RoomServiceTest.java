package com.sahyunjin.prographyspringquest.service;

import com.sahyunjin.prographyspringquest.domain.room.Room;
import com.sahyunjin.prographyspringquest.domain.room.RoomJpaRepository;
import com.sahyunjin.prographyspringquest.domain.user.User;
import com.sahyunjin.prographyspringquest.domain.user.UserJpaRepository;
import com.sahyunjin.prographyspringquest.domain.userroom.UserRoom;
import com.sahyunjin.prographyspringquest.domain.userroom.UserRoomJpaRepository;
import com.sahyunjin.prographyspringquest.dto.room.RoomSaveRequestDto;
import com.sahyunjin.prographyspringquest.service.logic.RoomServiceLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @InjectMocks
    private RoomServiceLogic roomServiceLogic;
    @Mock
    private RoomJpaRepository roomJpaRepository;
    @Mock
    private UserJpaRepository userJpaRepository;
    @Mock
    private UserRoomJpaRepository userRoomJpaRepository;

    User user; Room room; UserRoom userRoom;
    @BeforeEach
    public void beforeEach() {
        user = User.UserTestBuilder().id(1).build();
        room = Room.RoomTestBuilder().id(1).build();
        userRoom = UserRoom.UserRoomTestBuilder().id(1).build();
    }


    @Test
    @DisplayName("방 생성_Test")
    void createRoom_test() {

        // given
        RoomSaveRequestDto roomSaveRequestDto = new RoomSaveRequestDto();
        roomSaveRequestDto.setIdAndRoomType(1, "DOUBLE");

        when(userJpaRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(userRoomJpaRepository.existsByUserId(anyInt())).thenReturn(false);
        when(roomJpaRepository.save(any(Room.class))).thenReturn(room);
        when(userRoomJpaRepository.save(any(UserRoom.class))).thenReturn(userRoom);

        // when
        roomServiceLogic.createRoom(roomSaveRequestDto);

        // then
        verify(userJpaRepository, times(1)).findById(anyInt());
        verify(userRoomJpaRepository, times(1)).existsByUserId(anyInt());
        verify(roomJpaRepository, times(1)).save(any(Room.class));
        verify(userRoomJpaRepository, times(1)).save(any(UserRoom.class));
    }
}
