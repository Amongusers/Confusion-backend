package com.example.amongserver.authrization.contoller;

import com.example.amongserver.authrization.dto.UserAuthErrorResponseDto;
import com.example.amongserver.authrization.dto.UserAuthRequestDto;
import com.example.amongserver.authrization.dto.UserAuthResponseDto;
import com.example.amongserver.authrization.service.JwtTokenService;
import com.example.amongserver.registration.dto.UserRegisterErrorResponseDto;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "контроллер авторизации", tags = {"авторизация", "пользователи"},
        produces = "application/json",
        consumes = "application/json", protocols = "http")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserAuthController {

    private final JwtTokenService jwtTokenService;

    @ApiOperation(value = "авторищация пользователя",
            notes = "Авторизация пользователя с полями email и password."  +
                    "404 ошибку нужно вынести в авторизацию, исправлю." +
                    "Будут ещё другие исключения, пока что не реализовал.",
            httpMethod = "POST",
            nickname = "authUser"
    )
    @ApiResponses(value = {@ApiResponse(code = 200, message = "успешная авторизация",
        response = UserAuthResponseDto.class),
            @ApiResponse(code = 401, message = "Пароль или email не верные",
                    response = UserAuthErrorResponseDto.class)})
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public UserAuthResponseDto authUser(@ApiParam(value = "email, password",
            required = true,
            type = "UserAuthRequestDto") @RequestBody
                                            @Valid UserAuthRequestDto userAuthRequestDto) {
        return jwtTokenService.authUser(userAuthRequestDto);
    }
}
