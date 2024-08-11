package com.example.amongserver.controller.websocketcontroller;


import com.example.amongserver.dto.UserGeoPositionDto;
import com.example.amongserver.service.UserGeoPositionService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        List<UserGeoPositionDto> userGeoPositionDtoList
                = userGeoPositionService.updateGeoPosition(userGeoPositionDto);
        if (userGeoPositionDtoList!=null) {
            sendMessageToGeoPosition(userGeoPositionDtoList);
        }
    }


    private void sendMessageToGeoPosition(List<UserGeoPositionDto> userGeoPositionDtoList) {
        // если сообщение отправляется в общий чат
        simpleMessageTemplate.convertAndSend(GEOPOS_TOPIC, userGeoPositionDtoList);
    }
}
