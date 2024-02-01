package com.sahyunjin.prographyspringquest.service;

import com.sahyunjin.prographyspringquest.domain.room.Room;
import com.sahyunjin.prographyspringquest.domain.room.RoomJpaRepository;
import com.sahyunjin.prographyspringquest.domain.room.RoomStatus;
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

import static org.assertj.core.api.Assertions.assertThat;
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
    Integer roomId; UserRoomAttentionRequestDto userRoomAttentionRequestDto;
    @BeforeEach
    public void beforeEach() {
        user = User.UserTestBuilder().id(1).build();
        room = Room.RoomTestBuilder().id(1).build();
        userRoom = UserRoom.UserRoomTestBuilder().id(1).build();

        roomId = 1;
        userRoomAttentionRequestDto = new UserRoomAttentionRequestDto();
        userRoomAttentionRequestDto.setId(roomId);
    }


    @Test
    @DisplayName("방 참가_Test")
    void attentionRoom_test() {

        // given
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

    @Test
    @DisplayName("방 나가기_호스트일때_Test")
    void outRoom_Host_test() {  // 방을 나가려는 사람이 호스트일 경우의 테스트

        // given
        when(roomJpaRepository.findById(roomId)).thenReturn(Optional.of(room));
        when(userJpaRepository.existsById(userRoomAttentionRequestDto.getUserId())).thenReturn(true);
        when(userRoomJpaRepository.findByUserIdAndRoomId(userRoomAttentionRequestDto.getUserId(), roomId)).thenReturn(Optional.of(userRoom));

        // when - 조건1 (방을 나가려는 사람이 호스트일때)
        userRoomServiceLogic.outRoom(roomId, userRoomAttentionRequestDto);

        // then - 조건1 (방을 나가려는 사람이 호스트일때)
        verify(userRoomJpaRepository, times(1)).deleteAllByRoomId(roomId);
        assertThat(room.getStatus()).isEqualTo(RoomStatus.FINISH);
        verify(userRoomJpaRepository, times(0)).delete(userRoom);
    }

    @Test
    @DisplayName("방 나가기_호스트아닐때_Test")
    void outRoom_NotHost_test() {  // 방을 나가려는 사람이 호스트가 아닐 경우의 테스트

        // given
        userRoomAttentionRequestDto.setId(2);
        when(roomJpaRepository.findById(roomId)).thenReturn(Optional.of(room));
        when(userJpaRepository.existsById(userRoomAttentionRequestDto.getUserId())).thenReturn(true);
        when(userRoomJpaRepository.findByUserIdAndRoomId(userRoomAttentionRequestDto.getUserId(), roomId)).thenReturn(Optional.of(userRoom));

        // when - 조건2 (방을 나가려는 사람이 호스트가 아닐때)
        userRoomServiceLogic.outRoom(roomId, userRoomAttentionRequestDto);

        // then - 조건2 (방을 나가려는 사람이 호스트가 아닐때)
        verify(userRoomJpaRepository, times(0)).deleteAllByRoomId(roomId);
        assertThat(room.getStatus()).isNotEqualTo(RoomStatus.FINISH);
        verify(userRoomJpaRepository, times(1)).delete(userRoom);
    }

}
