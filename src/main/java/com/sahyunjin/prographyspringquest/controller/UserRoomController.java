package com.sahyunjin.prographyspringquest.controller;

import com.sahyunjin.prographyspringquest.dto.userroom.UserRoomAttentionRequestDto;
import com.sahyunjin.prographyspringquest.response.ApiResponseData;
import com.sahyunjin.prographyspringquest.response.ResponseCode;
import com.sahyunjin.prographyspringquest.response.swagger.Response_200;
import com.sahyunjin.prographyspringquest.response.swagger.Response_201;
import com.sahyunjin.prographyspringquest.response.swagger.Response_500;
import com.sahyunjin.prographyspringquest.service.UserRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@Tag(name = "UserRoom")
@RestController
@RequiredArgsConstructor
public class UserRoomController {

    private final UserRoomService userRoomService;


    @Operation(summary = "방 참가 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = {@Content(schema = @Schema(implementation = Response_200.class))}),
            @ApiResponse(responseCode = "201", description = "잘못된 요청 (문제 명시 - 실제 HTTP 상태코드는 200으로 고정)", content = {@Content(schema = @Schema(implementation = Response_201.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러 (문제 명시 - 실제 HTTP 상태코드는 200으로 고정)", content = {@Content(schema = @Schema(implementation = Response_500.class))})
    })
    @PostMapping("/room/attention/{roomId}")
    public ResponseEntity attentionRoom(
            @Parameter(name = "roomId") @PathVariable Integer roomId,
            @RequestBody UserRoomAttentionRequestDto userRoomAttentionRequestDto
    ) {
        userRoomService.attentionRoom(roomId, userRoomAttentionRequestDto);
        return ApiResponseData.toResponseEntity(ResponseCode.SUCCESS);
    }


    @Operation(summary = "방 나가기 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = {@Content(schema = @Schema(implementation = Response_200.class))}),
            @ApiResponse(responseCode = "201", description = "잘못된 요청 (문제 명시 - 실제 HTTP 상태코드는 200으로 고정)", content = {@Content(schema = @Schema(implementation = Response_201.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러 (문제 명시 - 실제 HTTP 상태코드는 200으로 고정)", content = {@Content(schema = @Schema(implementation = Response_500.class))})
    })
    @PostMapping("/room/out/{roomId}")
    public ResponseEntity outRoom(
            @Parameter(name = "roomId") @PathVariable Integer roomId,
            @RequestBody UserRoomAttentionRequestDto userRoomAttentionRequestDto
    ) {  // UserRoomAttentionRequestDto 클래스를 재사용하겠음.
        userRoomService.outRoom(roomId, userRoomAttentionRequestDto);
        return ApiResponseData.toResponseEntity(ResponseCode.SUCCESS);
    }


    @Operation(summary = "게임시작 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = {@Content(schema = @Schema(implementation = Response_200.class))}),
            @ApiResponse(responseCode = "201", description = "잘못된 요청 (문제 명시 - 실제 HTTP 상태코드는 200으로 고정)", content = {@Content(schema = @Schema(implementation = Response_201.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러 (문제 명시 - 실제 HTTP 상태코드는 200으로 고정)", content = {@Content(schema = @Schema(implementation = Response_500.class))})
    })
    @PutMapping("/room/start/{roomId}")
    public ResponseEntity gameStart(
            @Parameter(name = "roomId") @PathVariable Integer roomId,
            @RequestBody UserRoomAttentionRequestDto userRoomAttentionRequestDto
    ) {  // UserRoomAttentionRequestDto 클래스를 재사용하겠음.
        userRoomService.gameStart(roomId, userRoomAttentionRequestDto);
        return ApiResponseData.toResponseEntity(ResponseCode.SUCCESS);
    }


    @Operation(summary = "팀 변경 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = {@Content(schema = @Schema(implementation = Response_200.class))}),
            @ApiResponse(responseCode = "201", description = "잘못된 요청 (문제 명시 - 실제 HTTP 상태코드는 200으로 고정)", content = {@Content(schema = @Schema(implementation = Response_201.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러 (문제 명시 - 실제 HTTP 상태코드는 200으로 고정)", content = {@Content(schema = @Schema(implementation = Response_500.class))})
    })
    @PutMapping("/team/{roomId}")
    public ResponseEntity changeTeam(
            @Parameter(name = "roomId") @PathVariable Integer roomId,
            @RequestBody UserRoomAttentionRequestDto userRoomAttentionRequestDto
    ) {  // UserRoomAttentionRequestDto 클래스를 재사용하겠음.
        userRoomService.changeTeam(roomId, userRoomAttentionRequestDto);
        return ApiResponseData.toResponseEntity(ResponseCode.SUCCESS);
    }
}
