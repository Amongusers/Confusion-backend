package com.example.amongserver.jwt.contoller;

import com.example.amongserver.jwt.dto.JwtRequestDto;
import com.example.amongserver.jwt.dto.JwtResponseDto;
import com.example.amongserver.jwt.service.JwtTokenService;
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
