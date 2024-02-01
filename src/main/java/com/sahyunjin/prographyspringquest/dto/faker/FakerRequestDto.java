package com.sahyunjin.prographyspringquest.dto.faker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FakerRequestDto {

    private Integer seed;
    private Integer quantity;
}
