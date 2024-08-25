package com.example.amongserver.registration.controller;

import com.example.amongserver.registration.dto.UserProfileDto;
import com.example.amongserver.registration.dto.UserRegisterRequestDto;
import com.example.amongserver.registration.service.UserRegisterService;
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
    public UserProfileDto registerUser(@RequestBody UserRegisterRequestDto userRegisterRequestDto) {
        return userRegisterService.add(userRegisterRequestDto);
    }
}
