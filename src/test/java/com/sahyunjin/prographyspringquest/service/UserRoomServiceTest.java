package com.sahyunjin.prographyspringquest.service;

import com.sahyunjin.prographyspringquest.domain.room.Room;
import com.sahyunjin.prographyspringquest.domain.room.RoomJpaRepository;
import com.sahyunjin.prographyspringquest.domain.user.User;
import com.sahyunjin.prographyspringquest.domain.user.UserJpaRepository;
import com.sahyunjin.prographyspringquest.domain.userroom.UserRoom;
import com.sahyunjin.prographyspringquest.domain.userroom.UserRoomJpaRepository;
import com.sahyunjin.prographyspringquest.dto.userroom.UserRoomAttentionRequestDto;
import com.sahyunjin.prographyspringquest.service.logic.UserRoomServiceLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRoomServiceTest {

    @InjectMocks
    private UserRoomServiceLogic userRoomServiceLogic;
    @Mock
    private UserJpaRepository userJpaRepository;
    @Mock
    private RoomJpaRepository roomJpaRepository;
    @Mock
    private UserRoomJpaRepository userRoomJpaRepository;
    @Mock
    private TransactionTemplate transactionTemplate;

    User user; Room room; UserRoom userRoom;
    @BeforeEach
    public void beforeEach() {
        user = User.UserTestBuilder().id(1).build();
        room = Room.RoomTestBuilder().id(1).build();
        userRoom = UserRoom.UserRoomTestBuilder().id(1).build();
    }


    @Test
    @DisplayName("방 참가_Test")
    void attentionRoom_test() {

        // given
        Integer roomId = 1;
        UserRoomAttentionRequestDto userRoomAttentionRequestDto = new UserRoomAttentionRequestDto();
        userRoomAttentionRequestDto.setId(roomId);

        List<UserRoom> userRoomList = new ArrayList<>();

        when(roomJpaRepository.findById(roomId)).thenReturn(Optional.of(room));
        when(userJpaRepository.findById(userRoomAttentionRequestDto.getUserId())).thenReturn(Optional.of(user));
        when(userRoomJpaRepository.existsByUserId(user.getId())).thenReturn(false);
        when(userRoomJpaRepository.findAllByRoomId(roomId)).thenReturn(userRoomList);

        // when
        userRoomServiceLogic.attentionRoom(roomId, userRoomAttentionRequestDto);

        // then
        verify(userRoomJpaRepository, times(1)).save(any(UserRoom.class));
    }

}
