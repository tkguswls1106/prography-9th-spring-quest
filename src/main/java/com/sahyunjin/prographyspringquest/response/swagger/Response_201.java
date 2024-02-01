package com.sahyunjin.prographyspringquest.response.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class Response_201 {

    @Schema(example = "201")
    private Integer code;

    @Schema(example = "불가능한 요청입니다.")
    private String message;
}