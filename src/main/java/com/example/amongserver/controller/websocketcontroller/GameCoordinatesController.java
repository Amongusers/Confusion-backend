package com.example.amongserver.controller.websocketcontroller;

import com.example.amongserver.domain.entity.StartCoordinates;
import com.example.amongserver.service.StartCoordinatesService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.example.amongserver.constant.Const.COORDINATES_TOPIC;
import static com.example.amongserver.constant.Const.LINK_CHAT;

@RestController
@RequestMapping(LINK_CHAT)
@AllArgsConstructor
public class GameCoordinatesController {
    private final SimpMessagingTemplate simpleMessageTemplate;
    private final StartCoordinatesService service;

    @MessageMapping("/coordinates")
    public void coordinatesSocket(StartCoordinates coordinates) {
        System.out.println("34 id=" + coordinates.getId() + " x=" + coordinates.getLatitude() + " y=" + coordinates.getLongitude() + "sfd");
        if (coordinates.isCompleted()) {
            StartCoordinates existingCoordinates = service.getById(coordinates.getId());

            // Обновляем значение поля coordinates
            existingCoordinates.setCompleted(true);

            // Сохраняем изменения в базе данных
            service.add(existingCoordinates);


            List<StartCoordinates> startCoordinatesList = service.getAll();;
            List<StartCoordinates> trueConditionCoordinates = new ArrayList<>();

            for (StartCoordinates startCoordinates : startCoordinatesList) {
                if (!startCoordinates.isCompleted()) {
                    trueConditionCoordinates.add(startCoordinates);
                }
            }

            sendCoordinatesToClients(trueConditionCoordinates);
        }
    }

    private void sendCoordinatesToClients(List<StartCoordinates> coordinatesList) {
        simpleMessageTemplate.convertAndSend(COORDINATES_TOPIC, coordinatesList);
    }

}
