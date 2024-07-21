package com.example.amongserver.controller.websocketcontroller;


import com.example.amongserver.dto.UserGeoPositionDto;
import com.example.amongserver.service.UserGeoPositionService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.amongserver.constant.Const.GEOPOS_TOPIC;
import static com.example.amongserver.constant.Const.LINK_CHAT;
/*
WebSockets контроллер
Используется для отправки координат пользователей
Принимается изменения у пользователя, сохраняет их в БД
и отправлет другим участникам сессии
*/
@RestController
@RequestMapping(LINK_CHAT)
@AllArgsConstructor
public class GeoPositionController {
    private final SimpMessagingTemplate simpleMessageTemplate;
    private final UserGeoPositionService userGeoPositionService;

    @MessageMapping("/sock")
    public void geoPosSocket(UserGeoPositionDto userGeoPositionDto) {
        UserGeoPositionDto userGeoPositionDtoUpdate
                = userGeoPositionService.updateGeoPosition(userGeoPositionDto);
        if (userGeoPositionDtoUpdate!=null) {
            sendMessageToGeoPosition(userGeoPositionDtoUpdate);
        }
    }


    private void sendMessageToGeoPosition(UserGeoPositionDto userGeoPositionDto) {
        // если сообщение отправляется в общий чат
        simpleMessageTemplate.convertAndSend(GEOPOS_TOPIC, userGeoPositionDto);
    }
}
