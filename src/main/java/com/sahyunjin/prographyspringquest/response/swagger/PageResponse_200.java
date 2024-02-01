package com.sahyunjin.prographyspringquest.response.swagger;

import com.sahyunjin.prographyspringquest.dto.user.PageResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PageResponse_200 {

    @Schema(example = "200")
    private Integer code;

    @Schema(example = "API 요청이 성공했습니다.")
    private String message;

    @Schema
    private PageResponseDto result;
}
