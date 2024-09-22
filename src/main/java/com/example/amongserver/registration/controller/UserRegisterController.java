package com.example.amongserver.registration.controller;

import com.example.amongserver.registration.dto.UserRegisterErrorResponseDto;
import com.example.amongserver.registration.dto.UserRegisterRequestDto;
import com.example.amongserver.registration.service.UserRegisterService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "контроллер регесрации", tags = {"регестрация", "пользователи"},
        produces = "application/json", protocols = "http")
@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class UserRegisterController {
    private final UserRegisterService userRegisterService;

    @ApiOperation(value = "регистрация нового пользователя",
            notes = "Регестрация пользователя с полями username, email и password." +
                    " Для ошибки роль не найдена возможно сделаю другой код, не 400." +
                    "404 ошибку нужно вынести в авторизацию, исправлю",
            httpMethod = "POST",
            nickname = "registerUser"
    )
    @ApiResponses(value = {@ApiResponse(code = 201, message = "успешная регестрация"),
            @ApiResponse(code = 409, message = "пользователь с таким email уже существует",
                    response = UserRegisterErrorResponseDto.class),
            @ApiResponse(code = 400, message = "Роль USER не инициализирована",
                    response = UserRegisterErrorResponseDto.class),
            @ApiResponse(code = 404, message = "Пользователь с данным email не найден",
                    response = UserRegisterErrorResponseDto.class),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервера",
                    response = UserRegisterErrorResponseDto.class)})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@ApiParam(value = "username, email, password",
            required = true,
            type = "UserRegisterRequestDto"
    ) @RequestBody @Valid UserRegisterRequestDto userRegisterRequestDto) {
        userRegisterService.saveUser(userRegisterRequestDto);
    }
}
