package com.sahyunjin.prographyspringquest.response.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class Response_200 {

    @Schema(example = "200")
    private Integer code;

    @Schema(example = "API 요청이 성공했습니다.")
    private String message;
}