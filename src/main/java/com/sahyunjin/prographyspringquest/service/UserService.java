package com.sahyunjin.prographyspringquest.service;

import com.sahyunjin.prographyspringquest.dto.faker.FakerRequestDto;
import com.sahyunjin.prographyspringquest.dto.user.PageResponseDto;

public interface UserService {

    void initFakeUsers(FakerRequestDto fakerRequestDto);
    PageResponseDto findUsers(Integer size, Integer page);
}
