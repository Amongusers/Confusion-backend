package com.example.amongserver.controller.websocketcontroller;


import com.example.amongserver.domain.entity.GameState;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.amongserver.constant.Const.*;

@RestController
@RequestMapping(LINK_CHAT)
@AllArgsConstructor
public class GameStateController {
    private final SimpMessagingTemplate simpleMessageTemplate;


    @MessageMapping("/gema")
    public void geoPosSocket(GameState res) {
        sendMessageToGeoPosition(res); // отправим сообщения другим пользователям
    }

    private void sendMessageToGeoPosition(GameState message) {
        // если сообщение отправляется в общий чат
        simpleMessageTemplate.convertAndSend(GEMA_TOPIC, message);
    }
}