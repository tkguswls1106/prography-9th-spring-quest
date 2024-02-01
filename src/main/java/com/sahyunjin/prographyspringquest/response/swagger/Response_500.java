package com.sahyunjin.prographyspringquest.response.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class Response_500 {

    @Schema(example = "500")
    private Integer code;

    @Schema(example = "에러가 발생했습니다.")
    private String message;
}
