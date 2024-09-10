package com.example.amongserver.authrization.contoller;

import com.example.amongserver.authrization.dto.UserAuthRequestDto;
import com.example.amongserver.authrization.dto.UserAuthResponseDto;
import com.example.amongserver.authrization.service.JwtTokenService;
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
    public UserAuthResponseDto authUser(@RequestBody @Valid UserAuthRequestDto userAuthRequestDto) {
        return jwtTokenService.authUser(userAuthRequestDto);
    }
}
