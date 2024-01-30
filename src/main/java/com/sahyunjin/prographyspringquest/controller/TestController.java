package com.sahyunjin.prographyspringquest.controller;

import com.sahyunjin.prographyspringquest.response.ApiResponse;
import com.sahyunjin.prographyspringquest.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/health")
    public ResponseEntity healthCheck() {
        return ApiResponse.toResponseEntity(ResponseCode.SUCCESS);
    }
}
