package com.sahyunjin.prographyspringquest.externalapi;

import com.sahyunjin.prographyspringquest.dto.faker.FakerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "faker", url = "https://fakerapi.it/api/v1/")
public interface FakerClient {

    @GetMapping("/users?_seed={seed}&_quantity={quantity}&_locale=ko_KR")
    FakerResponseDto makeFakeUsers(@PathVariable("seed") Integer seed, @PathVariable("quantity") Integer quantity);
}
