package com.sahyunjin.prographyspringquest.dto.faker;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FakerResponseDto {

    public String status;
    public Integer code;
    public Integer total;
    public List<FakerDataResponseDto> data;


    public void setTotal(Integer total) {  // 테스트 전용 set매소드
        this.total = total;
    }
}
