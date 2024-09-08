package com.example.amongserver.authorization.controller;

import com.example.amongserver.authorization.dto.UserProfileDto;
import com.example.amongserver.authorization.dto.UserRegisterRequestDto;
import com.example.amongserver.authorization.service.UserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class UserRegisterController {
    private final UserRegisterService userRegisterService;
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody UserRegisterRequestDto userRegisterRequestDto) {
        userRegisterService.add(userRegisterRequestDto);
    }
}
