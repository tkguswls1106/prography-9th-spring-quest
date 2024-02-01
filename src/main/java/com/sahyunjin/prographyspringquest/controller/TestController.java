package com.sahyunjin.prographyspringquest.controller;

import com.sahyunjin.prographyspringquest.response.ApiResponseData;
import com.sahyunjin.prographyspringquest.response.ResponseCode;
import com.sahyunjin.prographyspringquest.response.swagger.Response_200;
import com.sahyunjin.prographyspringquest.response.swagger.Response_500;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@Tag(name = "Health")
@RestController
@RequiredArgsConstructor
public class TestController {

    @Operation(summary = "헬스체크 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = {@Content(schema = @Schema(implementation = Response_200.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러 (문제 명시 - 실제 HTTP 상태코드는 200으로 고정)", content = {@Content(schema = @Schema(implementation = Response_500.class))})
    })
    @GetMapping("/health")
    public ResponseEntity healthCheck() {
        return ApiResponseData.toResponseEntity(ResponseCode.SUCCESS);
    }
}
