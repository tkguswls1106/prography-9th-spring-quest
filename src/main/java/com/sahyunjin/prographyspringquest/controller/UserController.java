package com.sahyunjin.prographyspringquest.controller;

import com.sahyunjin.prographyspringquest.dto.faker.FakerRequestDto;
import com.sahyunjin.prographyspringquest.dto.user.PageResponseDto;
import com.sahyunjin.prographyspringquest.response.ApiResponseData;
import com.sahyunjin.prographyspringquest.response.ResponseCode;
import com.sahyunjin.prographyspringquest.response.swagger.PageResponse_200;
import com.sahyunjin.prographyspringquest.response.swagger.Response_200;
import com.sahyunjin.prographyspringquest.response.swagger.Response_500;
import com.sahyunjin.prographyspringquest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@Tag(name = "User")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @Operation(summary = "초기화 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = {@Content(schema = @Schema(implementation = Response_200.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러 (문제 명시 - 실제 HTTP 상태코드는 200으로 고정)", content = {@Content(schema = @Schema(implementation = Response_500.class))})
    })
    @GetMapping("/init")
    public ResponseEntity initUsers(@RequestBody FakerRequestDto fakerRequestDto) {
        userService.initFakeUsers(fakerRequestDto);
        return ApiResponseData.toResponseEntity(ResponseCode.SUCCESS);
    }


    @Operation(summary = "유저 전체 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = {@Content(schema = @Schema(implementation = PageResponse_200.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러 (문제 명시 - 실제 HTTP 상태코드는 200으로 고정)", content = {@Content(schema = @Schema(implementation = Response_500.class))})
    })
    @GetMapping("/user")
    public ResponseEntity findUsers(@RequestParam("size") Integer size, @RequestParam("page") Integer page) {
        PageResponseDto pageResponseDto = userService.findUsers(size, page);
        return ApiResponseData.toResponseEntity(ResponseCode.SUCCESS, pageResponseDto);
    }
}
