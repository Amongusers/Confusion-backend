package com.example.amongserver.controller.restcontroller;

import com.example.amongserver.dto.UserGameDto;
import com.example.amongserver.dto.UserRegisterDto;
import com.example.amongserver.service.UserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class UserRegisterController {
    private final UserRegisterService userRegisterService;
    @PostMapping()
    public void registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        userRegisterService.add(userRegisterDto);
    }
}
