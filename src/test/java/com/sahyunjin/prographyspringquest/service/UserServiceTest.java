package com.sahyunjin.prographyspringquest.service;

import com.sahyunjin.prographyspringquest.domain.user.UserJpaRepository;
import com.sahyunjin.prographyspringquest.dto.faker.FakerDataResponseDto;
import com.sahyunjin.prographyspringquest.dto.faker.FakerRequestDto;
import com.sahyunjin.prographyspringquest.dto.faker.FakerResponseDto;
import com.sahyunjin.prographyspringquest.service.logic.FakerServiceLogic;
import com.sahyunjin.prographyspringquest.service.logic.UserServiceLogic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

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
}
