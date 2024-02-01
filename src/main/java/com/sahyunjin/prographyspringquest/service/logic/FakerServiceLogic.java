package com.sahyunjin.prographyspringquest.service.logic;

import com.sahyunjin.prographyspringquest.dto.faker.FakerRequestDto;
import com.sahyunjin.prographyspringquest.dto.faker.FakerResponseDto;
import com.sahyunjin.prographyspringquest.externalapi.FakerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FakerServiceLogic {

    private final FakerClient fakerClient;

    @Transactional
    public FakerResponseDto makeFakeUsers(FakerRequestDto fakerRequestDto) {
        return fakerClient.makeFakeUsers(fakerRequestDto.getSeed(), fakerRequestDto.getQuantity(), "ko_KR");
    }
}
