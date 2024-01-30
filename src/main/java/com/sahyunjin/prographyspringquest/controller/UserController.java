package com.sahyunjin.prographyspringquest.controller;

import com.sahyunjin.prographyspringquest.dto.faker.FakerRequestDto;
import com.sahyunjin.prographyspringquest.response.ApiResponse;
import com.sahyunjin.prographyspringquest.response.ResponseCode;
import com.sahyunjin.prographyspringquest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/init")
    public ResponseEntity initUsers(@RequestBody FakerRequestDto fakerRequestDto) {
        userService.initFakeUsers(fakerRequestDto);
        return ApiResponse.toResponseEntity(ResponseCode.SUCCESS);
    }
}
