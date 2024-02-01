package com.sahyunjin.prographyspringquest.dto.faker;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class FakerResponseDto {

    private String status;
    private Integer code;
    private Integer total;
    private List<FakerDataResponseDto> data;


    // 테스트 전용 set매소드
    public void setTotal(Integer total) {
        this.total = total;
    }
    public void addData(FakerDataResponseDto fakerDataResponseDto) {
        this.data = new ArrayList<>();
        this.data.add(fakerDataResponseDto);
    }
}
