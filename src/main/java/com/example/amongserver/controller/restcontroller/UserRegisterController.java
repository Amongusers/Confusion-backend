package com.example.amongserver.controller.restcontroller;

import com.example.amongserver.dto.UserProfileDto;
import com.example.amongserver.dto.UserRegisterRequestDto;
import com.example.amongserver.service.UserRegisterService;
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
