package com.example.amongserver.controller.restcontroller;

import com.example.amongserver.AmongServerApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/*
Rest контроллер
Используется для перезагрузки сервера с клиента (на клиенте добавлена времееная кнопка)
*/
@RestController
@RequiredArgsConstructor
public class RestartServerRestController {

    private final ConfigurableApplicationContext context;

    @GetMapping("/restart")
    public void restartServer() {
        AmongServerApplication.restart();
    }
}
