package com.example.amongserver.controller.websocketcontroller;

import com.example.amongserver.dto.GameCoordinatesDto;
import com.example.amongserver.service.impl.GameCoordinatesService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.amongserver.constant.Const.COORDINATES_TOPIC;
import static com.example.amongserver.constant.Const.LINK_CHAT;
/*
WebSockets контроллер
Используется для отправки актуальных игровых координат (заданий)
*/
@RestController
@RequestMapping(LINK_CHAT)
@AllArgsConstructor
public class GameCoordinatesController {
    private final SimpMessagingTemplate simpleMessageTemplate;
    private final GameCoordinatesService gameCoordinatesService;

    @MessageMapping("/coordinates")
    public void coordinatesSocket(GameCoordinatesDto gameCoordinatesDto) {

        sendCoordinatesToClients(gameCoordinatesService.getAllIsCompleted(gameCoordinatesDto));
    }

    private void sendCoordinatesToClients(List<GameCoordinatesDto> coordinatesList) {
        simpleMessageTemplate.convertAndSend(COORDINATES_TOPIC, coordinatesList);
    }

}
