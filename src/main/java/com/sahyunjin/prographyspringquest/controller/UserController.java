package com.sahyunjin.prographyspringquest.controller;

import com.sahyunjin.prographyspringquest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;



}
