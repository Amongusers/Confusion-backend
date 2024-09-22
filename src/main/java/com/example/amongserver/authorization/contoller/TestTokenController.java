package com.example.amongserver.authorization.contoller;

import com.example.amongserver.authorization.exception.handler.JwtTokenExceptionHandler;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
@Api(value = "тестовый контроллер для проверки токена", tags = {"тест"})
@RestController
@JwtTokenExceptionHandler
public class TestTokenController {

    @PostMapping("/test")
    public String testToken() {
        return "Токен валиден";
    }
}
