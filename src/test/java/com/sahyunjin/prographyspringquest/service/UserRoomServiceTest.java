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
    // 밑은 시간경과후 변화하는지 확인해야하므로, 테스트에서 제외시키겠음.
//    @Mock
//    private TransactionTemplate transactionTemplate;

    User user; Room room; UserRoom userRoom;
    Room mockRoom;
    Integer roomId; UserRoomAttentionRequestDto userRoomAttentionRequestDto;
    @BeforeEach
    public void beforeEach() {
        user = User.UserTestBuilder().id(1).build();
        room = Room.RoomTestBuilder().id(1).build();
        userRoom = UserRoom.UserRoomTestBuilder().id(1).build();

        // update 관련 메소드는 엔티티클래스 내부에 직접 선언되어있으므로,
        // 나중에 update메소드 실행 확인할때 위의 room같은 실제 객체에 접근하면 verify 못하므로, spy로 가짜 모킹 객체를 만들어 기존 객체를 모방해줘야한다.
        mockRoom = spy(room);

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
        when(roomJpaRepository.findById(roomId)).thenReturn(Optional.of(mockRoom));
        when(userJpaRepository.existsById(userRoomAttentionRequestDto.getUserId())).thenReturn(true);
        when(userRoomJpaRepository.findByUserIdAndRoomId(userRoomAttentionRequestDto.getUserId(), roomId)).thenReturn(Optional.of(userRoom));

        // when - 조건1 (방을 나가려는 사람이 호스트일때)
        userRoomServiceLogic.outRoom(roomId, userRoomAttentionRequestDto);

        // then - 조건1 (방을 나가려는 사람이 호스트일때)
        verify(userRoomJpaRepository, times(1)).deleteAllByRoomId(roomId);
        verify(mockRoom, times(1)).updateRoomStatus(RoomStatus.FINISH);
        verify(userRoomJpaRepository, times(0)).delete(userRoom);

        // 밑은 mock객체가 아닌 실제 객체에 접근하므로, 단위 테스트의 범위를 벗어나는 행위이므로 실행하지 않겠음.
//        assertThat(room.getStatus()).isEqualTo(RoomStatus.FINISH);
    }

    @Test
    @DisplayName("방 나가기_호스트아닐때_Test")
    void outRoom_NotHost_test() {  // 방을 나가려는 사람이 호스트가 아닐 경우의 테스트

        // given
        userRoomAttentionRequestDto.setId(2);
        when(roomJpaRepository.findById(roomId)).thenReturn(Optional.of(mockRoom));
        when(userJpaRepository.existsById(userRoomAttentionRequestDto.getUserId())).thenReturn(true);
        when(userRoomJpaRepository.findByUserIdAndRoomId(userRoomAttentionRequestDto.getUserId(), roomId)).thenReturn(Optional.of(userRoom));

        // when - 조건2 (방을 나가려는 사람이 호스트가 아닐때)
        userRoomServiceLogic.outRoom(roomId, userRoomAttentionRequestDto);

        // then - 조건2 (방을 나가려는 사람이 호스트가 아닐때)
        verify(userRoomJpaRepository, times(0)).deleteAllByRoomId(roomId);
        verify(mockRoom, times(0)).updateRoomStatus(RoomStatus.FINISH);
        verify(userRoomJpaRepository, times(1)).delete(userRoom);

        // 밑은 mock객체가 아닌 실제 객체에 접근하므로, 단위 테스트의 범위를 벗어나는 행위이므로 실행하지 않겠음.
//        assertThat(room.getStatus()).isNotEqualTo(RoomStatus.FINISH);
    }

    @Test
    @DisplayName("게임시작_Test")
    void gameStart_test() {

        // given
        List<UserRoom> userRoomList = new ArrayList<>();
        for(int i=0; i<4; i++) userRoomList.add(userRoom);  // 현재 게임 내의 인원수: 4명 (이렇게되면 복식게임에서 인원수 모두 충족으로, 게임 시작 가능해짐.)

        when(roomJpaRepository.findById(roomId)).thenReturn(Optional.of(mockRoom));
        when(userJpaRepository.existsById(userRoomAttentionRequestDto.getUserId())).thenReturn(true);
        when(userRoomJpaRepository.existsByUserIdAndRoomId(userRoomAttentionRequestDto.getUserId(), roomId)).thenReturn(true);
        when(userRoomJpaRepository.findAllByRoomId(roomId)).thenReturn(userRoomList);

        // when
        userRoomServiceLogic.gameStart(roomId, userRoomAttentionRequestDto);

        // then
        verify(mockRoom, times(1)).updateRoomStatus(RoomStatus.PROGRESS);
    }

}
