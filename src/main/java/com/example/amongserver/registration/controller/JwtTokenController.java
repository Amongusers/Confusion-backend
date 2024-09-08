package com.example.amongserver.registration.controller;

import com.example.amongserver.registration.dto.JwtRequestDto;
import com.example.amongserver.registration.dto.JwtResponseDto;
import com.example.amongserver.registration.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class JwtTokenController {

    private final JwtTokenService jwtTokenService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public JwtResponseDto createAuthToken(@RequestBody /*@Valid*/ JwtRequestDto jwtRequestDto) {
        return new JwtResponseDto(jwtTokenService.getToken(jwtRequestDto));
    }
}
