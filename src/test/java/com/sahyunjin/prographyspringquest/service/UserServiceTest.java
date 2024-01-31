package com.sahyunjin.prographyspringquest.service;

import com.sahyunjin.prographyspringquest.domain.user.User;
import com.sahyunjin.prographyspringquest.domain.user.UserJpaRepository;
import com.sahyunjin.prographyspringquest.dto.faker.FakerDataResponseDto;
import com.sahyunjin.prographyspringquest.dto.faker.FakerRequestDto;
import com.sahyunjin.prographyspringquest.dto.faker.FakerResponseDto;
import com.sahyunjin.prographyspringquest.dto.user.PageResponseDto;
import com.sahyunjin.prographyspringquest.service.logic.FakerServiceLogic;
import com.sahyunjin.prographyspringquest.service.logic.UserServiceLogic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserServiceLogic userServiceLogic;
    @Mock
    UserJpaRepository userJpaRepository;
    @Mock
    FakerServiceLogic fakerServiceLogic;


    @Test
    @DisplayName("초기화_Test")
    void initFakeUsers_test() {

        // given
        FakerDataResponseDto fakerDataResponseDto = new FakerDataResponseDto();
        fakerDataResponseDto.setId(1);

        FakerResponseDto fakerResponseDto = new FakerResponseDto();
        fakerResponseDto.addData(fakerDataResponseDto);
        when(fakerServiceLogic.makeFakeUsers(any())).thenReturn(fakerResponseDto);

        // when
        FakerRequestDto fakerRequestDto = new FakerRequestDto();
        userServiceLogic.initFakeUsers(fakerRequestDto);

        // then
        verify(userJpaRepository, times(1)).deleteAll();
        verify(userJpaRepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("유저 전체 조회_Test")
    void findUsers_test() {

        // given
        Integer size = 10;
        Integer page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        List<User> users = new ArrayList<>();
        User user = User.UserTestBuilder()
                .id(1)
                .build();
        users.add(user);
        Page<User> userPage = new PageImpl<>(users);
        when(userJpaRepository.findAll(pageable)).thenReturn(userPage);
        // 이렇게되면 서비스코드의 Page<User> users = userJpaRepository.findAll(pageable); 부분이 실행될때, 위 테스트 코드의 userPage 객체가 users에 할당되게 된다.

        // when
        PageResponseDto pageResponseDto = userServiceLogic.findUsers(size, page);

        // then
        assertThat(pageResponseDto.getTotalElements()).isEqualTo(1);
        assertThat(pageResponseDto.getTotalPages()).isEqualTo(1);
        assertThat(pageResponseDto.getUserList().get(0).getId()).isEqualTo(1);
    }
}
