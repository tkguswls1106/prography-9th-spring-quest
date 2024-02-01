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
public class ApiResponseData<T> {  // Swagger 문서의 @ApiResponseData 어노테이션과 이름이 중복되어, ApiResponseData 대신 ApiResponseData 로 이름을 바꾸었음.

    private Integer code;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)  // result 값이 null일 경우 JSON 출력에서 제외시킴.
    private T result;

    // response result(실질적 데이터)가 없을때
    public static ResponseEntity<ApiResponseData> toResponseEntity(ResponseCode responseCode) {
        return ResponseEntity
                .status(200)  // 원래는 '.status(responseCode.getHttpStatus())'로 작성해야하지만, 문제에 맞추어 모든 http 실제 상태코드는 200으로 반환하도록 하겠다.
                .body(ApiResponseData.builder()
                        .code(responseCode.getHttpStatus())
                        .message(responseCode.getMessage())
                        .build()
                );
    }

    // response result(실질적 데이터)가 있을때
    public static <T> ResponseEntity<ApiResponseData<T>> toResponseEntity(ResponseCode responseCode, T result) {
        return ResponseEntity
                .status(200)  // 원래는 '.status(responseCode.getHttpStatus())'로 작성해야하지만, 문제에 맞추어 모든 http 실제 상태코드는 200으로 반환하도록 하겠다.
                .body(ApiResponseData.<T>builder()
                        .code(responseCode.getHttpStatus())
                        .message(responseCode.getMessage())
                        .result(result)
                        .build()
                );
    }

    // response result(실질적 데이터)와 header가 있을때
    public static <T> ResponseEntity<ApiResponseData<T>> toResponseEntity(ResponseCode responseCode, MultiValueMap<String, String> header, T result) {
        return ResponseEntity
                .status(200)  // 원래는 '.status(responseCode.getHttpStatus())'로 작성해야하지만, 문제에 맞추어 모든 http 실제 상태코드는 200으로 반환하도록 하겠다.
                .header(String.valueOf(header))
                .body(ApiResponseData.<T>builder()
                        .code(responseCode.getHttpStatus())
                        .message(responseCode.getMessage())
                        .result(result)
                        .build()
                );
    }
}
