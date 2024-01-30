package com.sahyunjin.prographyspringquest.externalapi;

import com.sahyunjin.prographyspringquest.dto.faker.FakerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "faker", url = "https://fakerapi.it/api/v1/")
public interface FakerClient {

    @GetMapping("/users")
    FakerResponseDto makeFakeUsers(@RequestParam("_seed") Integer seed, @RequestParam("_quantity") Integer quantity, @RequestParam("_locale") String locale);
}
