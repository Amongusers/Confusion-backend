package com.example.amongserver.authrization.contoller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestTokenController {

    @PostMapping("/test")
    public String testToken() {
        return "Токен валиден";
    }
}
