package com.example.amongserver.controller.websocketcontroller;


import com.example.amongserver.domain.GeoPosition;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;

import static com.example.amongserver.constant.Const.*;

@RestController
@RequestMapping(LINK_CHAT)
@AllArgsConstructor
public class GeoPositionController {
    private final SimpMessagingTemplate simpleMessageTemplate;
    private ArrayList<Long> deadPlayers = new ArrayList<>();

    @MessageMapping("/sock")
    public void geoPosSocket(GeoPosition res) {
        System.out.println(
                "RECEIVED: id=" + res.getId()
                        + " | latitude=" + res.getLatitude()
                        + " | longitude=" + res.getLongitude()
                        + " | isDead=" + res.isDead()
        );

        if (deadPlayers.contains(res.getId())) {
            System.out.println("Received player already dead!");
            return;
        }

        if (res.isDead()) {
            deadPlayers.add(res.getId());
        }

        sendMessageToGeoPosition(res); // отправим сообщения другим пользователям
    }


    private void sendMessageToGeoPosition(GeoPosition geoPosition) {
        // если сообщение отправляется в общий чат
        simpleMessageTemplate.convertAndSend(GEOPOS_TOPIC, geoPosition);
    }
}
