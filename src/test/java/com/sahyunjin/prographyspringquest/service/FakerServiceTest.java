package com.sahyunjin.prographyspringquest.service;

import com.sahyunjin.prographyspringquest.dto.faker.FakerRequestDto;
import com.sahyunjin.prographyspringquest.dto.faker.FakerResponseDto;
import com.sahyunjin.prographyspringquest.externalapi.FakerClient;
import com.sahyunjin.prographyspringquest.service.logic.FakerServiceLogic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FakerServiceTest {

    @InjectMocks
    FakerServiceLogic fakerServiceLogic;
    @Mock
    FakerClient fakerClient;


    @Test
    @DisplayName("가짜 사용자 데이터 반환_Test")
    void makeFakeUsers_Test() {

        // given  // 1. 테스트에서 이러한 것이 주어졌는데
        FakerResponseDto fakerResponseDto1 = new FakerResponseDto();
        fakerResponseDto1.setTotal(10);
        when(fakerClient.makeFakeUsers(any(), any(), any())).thenReturn(fakerResponseDto1);

        Integer seed = 123;
        Integer quantity = 10;  // faker 데이터 10개
        FakerRequestDto fakerRequestDto = new FakerRequestDto(seed, quantity);

        // when  // 2. 이것을 실행했을때
        FakerResponseDto fakerResponseDto2 = fakerServiceLogic.makeFakeUsers(fakerRequestDto);

        // then  // 3. 이러한 결과가 나와야한다
        assertThat(fakerResponseDto2.getTotal()).isEqualTo(fakerResponseDto1.getTotal());  // faker 데이터가 성공적으로 반환되었는가? (개수가 둘다 10개로 동일한지?)
    }
}
