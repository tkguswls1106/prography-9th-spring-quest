package com.sahyunjin.prographyspringquest.controller;

import com.sahyunjin.prographyspringquest.dto.room.RoomFindResponseDto;
import com.sahyunjin.prographyspringquest.dto.room.RoomPageResponseDto;
import com.sahyunjin.prographyspringquest.dto.room.RoomSaveRequestDto;
import com.sahyunjin.prographyspringquest.response.ApiResponseData;
import com.sahyunjin.prographyspringquest.response.ResponseCode;
import com.sahyunjin.prographyspringquest.response.swagger.*;
import com.sahyunjin.prographyspringquest.service.RoomService;
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
@Tag(name = "Room")
@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;


    @Operation(summary = "방 생성 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = {@Content(schema = @Schema(implementation = Response_200.class))}),
            @ApiResponse(responseCode = "201", description = "잘못된 요청 (문제 명시 - 실제 HTTP 상태코드는 200으로 고정)", content = {@Content(schema = @Schema(implementation = Response_201.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러 (문제 명시 - 실제 HTTP 상태코드는 200으로 고정)", content = {@Content(schema = @Schema(implementation = Response_500.class))})
    })
    @PostMapping("/room")
    public ResponseEntity createRoom(@RequestBody RoomSaveRequestDto roomSaveRequestDto) {
        roomService.createRoom(roomSaveRequestDto);
        return ApiResponseData.toResponseEntity(ResponseCode.SUCCESS);
    }


    @Operation(summary = "방 전체 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = {@Content(schema = @Schema(implementation = RoomPageResponse_200.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러 (문제 명시 - 실제 HTTP 상태코드는 200으로 고정)", content = {@Content(schema = @Schema(implementation = Response_500.class))})
    })
    @GetMapping("/room")
    public ResponseEntity findRooms(@RequestParam("size") Integer size, @RequestParam("page") Integer page) {
        RoomPageResponseDto roomPageResponseDto = roomService.findRooms(size, page);
        return ApiResponseData.toResponseEntity(ResponseCode.SUCCESS, roomPageResponseDto);
    }


    @Operation(summary = "방 상세 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = {@Content(schema = @Schema(implementation = RoomFindResponse_200.class))}),
            @ApiResponse(responseCode = "201", description = "잘못된 요청 (문제 명시 - 실제 HTTP 상태코드는 200으로 고정)", content = {@Content(schema = @Schema(implementation = Response_201.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러 (문제 명시 - 실제 HTTP 상태코드는 200으로 고정)", content = {@Content(schema = @Schema(implementation = Response_500.class))})
    })
    @GetMapping("/room/{roomId}")
    public ResponseEntity findOneRoom(
            @Parameter(name = "roomId") @PathVariable Integer roomId
    ) {
        RoomFindResponseDto roomFindResponseDto = roomService.findOneRoom(roomId);
        return ApiResponseData.toResponseEntity(ResponseCode.SUCCESS, roomFindResponseDto);
    }
}
