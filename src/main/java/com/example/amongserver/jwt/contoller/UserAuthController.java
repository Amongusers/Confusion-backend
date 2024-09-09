package com.example.amongserver.jwt.contoller;

import com.example.amongserver.jwt.dto.UserAuthRequestDto;
import com.example.amongserver.jwt.dto.UserAuthResponseDto;
import com.example.amongserver.jwt.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserAuthController {

    private final JwtTokenService jwtTokenService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public UserAuthResponseDto createAuthToken(@RequestBody @Valid UserAuthRequestDto userAuthRequestDto) {
        return new UserAuthResponseDto(jwtTokenService.getToken(userAuthRequestDto));
    }
}
