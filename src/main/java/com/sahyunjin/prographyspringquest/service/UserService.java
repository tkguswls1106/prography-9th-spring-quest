package com.sahyunjin.prographyspringquest.service;

import com.sahyunjin.prographyspringquest.dto.faker.FakerRequestDto;
import com.sahyunjin.prographyspringquest.dto.faker.FakerResponseDto;

public interface UserService {

    void initFakeUsers(FakerRequestDto fakerRequestDto);
}
