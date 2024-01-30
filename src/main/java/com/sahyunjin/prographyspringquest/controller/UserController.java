package com.sahyunjin.prographyspringquest.controller;

import com.sahyunjin.prographyspringquest.dto.faker.FakerRequestDto;
import com.sahyunjin.prographyspringquest.dto.user.PageResponseDto;
import com.sahyunjin.prographyspringquest.response.ApiResponse;
import com.sahyunjin.prographyspringquest.response.ResponseCode;
import com.sahyunjin.prographyspringquest.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/init")
    public ResponseEntity initUsers(@RequestBody FakerRequestDto fakerRequestDto) {
        userService.initFakeUsers(fakerRequestDto);
        return ApiResponse.toResponseEntity(ResponseCode.SUCCESS);
    }

    @GetMapping("/user")
    public ResponseEntity findUsers(@RequestParam("size") Integer size, @RequestParam("page") Integer page) {
        PageResponseDto pageResponseDto = userService.findUsers(size, page);
        return ApiResponse.toResponseEntity(ResponseCode.SUCCESS, pageResponseDto);
    }

}
