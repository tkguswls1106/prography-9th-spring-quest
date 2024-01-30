package com.sahyunjin.prographyspringquest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

@Getter
@Builder
@ToString
public class ApiResponse<T> {

    private Integer code;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)  // result 값이 null일 경우 JSON 출력에서 제외시킴.
    private T result;

    // response result(실질적 데이터)가 없을때
    public static ResponseEntity<ApiResponse> toResponseEntity(ResponseCode responseCode) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(ApiResponse.builder()
                        .code(responseCode.getHttpStatus())
                        .message(responseCode.getMessage())
                        .build()
                );
    }

    // response result(실질적 데이터)가 있을때
    public static <T> ResponseEntity<ApiResponse<T>> toResponseEntity(ResponseCode responseCode, T result) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(ApiResponse.<T>builder()
                        .code(responseCode.getHttpStatus())
                        .message(responseCode.getMessage())
                        .result(result)
                        .build()
                );
    }

    // response result(실질적 데이터)와 header가 있을때
    public static <T> ResponseEntity<ApiResponse<T>> toResponseEntity(ResponseCode responseCode, MultiValueMap<String, String> header, T result) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .header(String.valueOf(header))
                .body(ApiResponse.<T>builder()
                        .code(responseCode.getHttpStatus())
                        .message(responseCode.getMessage())
                        .result(result)
                        .build()
                );
    }
}