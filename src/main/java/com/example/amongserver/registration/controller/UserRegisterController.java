package com.example.amongserver.registration.controller;

import com.example.amongserver.registration.dto.UserRegisterRequestDto;
import com.example.amongserver.registration.service.UserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class UserRegisterController {
    private final UserRegisterService userRegisterService;
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody @Valid UserRegisterRequestDto userRegisterRequestDto) {
        userRegisterService.saveUser(userRegisterRequestDto);
    }
}
