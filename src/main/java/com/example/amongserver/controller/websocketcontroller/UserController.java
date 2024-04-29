package com.example.amongserver.controller.websocketcontroller;


import com.example.amongserver.dto.UserGameDto;
import com.example.amongserver.service.UserGameDtoService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.amongserver.constant.Const.LINK_CHAT;
import static com.example.amongserver.constant.Const.USER_TOPIC;

@RestController
@RequestMapping(LINK_CHAT)
@AllArgsConstructor
public class UserController {
    private final SimpMessagingTemplate simpleMessageTemplate;
    private final UserGameDtoService service;
    @MessageMapping("/user")
    public void geoPosSocket(UserGameDto userGameDto) {
        UserGameDto localUser = service.getById(userGameDto.getId());
        // TODO: delete
        System.out.println(localUser);
        localUser.setReady(userGameDto.isReady());
        UserGameDto update = service.update(localUser.getId(), localUser);
        if (!update.isReady()) {
            System.out.println("Ошибка");
        } else {
            System.out.println("Всё чётко");
        }
        allReady();
    }


    private void allReady() {
        List<UserGameDto> userList = service.getAll();
        boolean isAllReady = true;
        for (UserGameDto user : userList) {
            if (!user.isReady()) {
                isAllReady = false;
                break;
            }
        }
        if (!isAllReady /*|| userList.size() < 3*/) {
            sendMessageToUser(userList);
        } else {
            createRole(userList);
        }
    }
    private void createRole(List<UserGameDto> userList) {
        for (UserGameDto localUser : userList) {
            localUser.setIsImposter(false);
        }
        if (!userList.isEmpty()) {
            int impostorIndex = (int) (Math.random() * userList.size());
            userList.get(impostorIndex).setIsImposter(true);
            sendMessageToUser(service.addAll(userList));
        }
    }
    private void sendMessageToUser(List<UserGameDto> userList) {
        // если сообщение отправляется в общий чат

        simpleMessageTemplate.convertAndSend(USER_TOPIC, userList);
    }
}
