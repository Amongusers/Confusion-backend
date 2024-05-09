package com.example.amongserver.controller.restcontroller;

import com.example.amongserver.AmongServerApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestartServerRestController {

    @Autowired
    private ConfigurableApplicationContext context;

    @GetMapping("/restart")
    public void restartServer() {
        new Thread(() -> {
            try {
                Thread.sleep(1000); // Добавим задержку для завершения текущих запросов
                SpringApplication.exit(context, () -> 0);
                SpringApplication.run(AmongServerApplication.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
