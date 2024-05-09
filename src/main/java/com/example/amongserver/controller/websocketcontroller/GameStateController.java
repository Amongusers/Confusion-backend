package com.example.amongserver.controller.websocketcontroller;


import com.example.amongserver.domain.entity.GameState;
import com.example.amongserver.service.GameStateService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.amongserver.constant.Const.GEMA_TOPIC;
import static com.example.amongserver.constant.Const.LINK_CHAT;

@RestController
@RequestMapping(LINK_CHAT)
@AllArgsConstructor
public class GameStateController {
    private final SimpMessagingTemplate simpleMessageTemplate;
    private final GameStateService gameStateService;


    @MessageMapping("/game")
    public void geoPosSocket(GameState gameState) {
        sendMessageToGeoPosition(gameStateService.update(gameState.getId(), gameState));
        // отправим сообщения другим пользователям
    }

    private void sendMessageToGeoPosition(GameState gameState) {
        // если сообщение отправляется в общий чат
        simpleMessageTemplate.convertAndSend(GEMA_TOPIC, gameState);
    }
}