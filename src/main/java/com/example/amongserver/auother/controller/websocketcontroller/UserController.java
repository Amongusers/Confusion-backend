package com.example.amongserver.auother.controller.websocketcontroller;


import com.example.amongserver.auother.dto.UserGameDto;
import com.example.amongserver.auother.service.UserGameDtoService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.amongserver.constant.Const.LINK_CHAT;
import static com.example.amongserver.constant.Const.USER_TOPIC;
/*
WebSockets контроллер
Используется в процессе всей игры
Принимается изменения у пользователя, сохраняет их в БД
и отправлет другим участникам сессии
*/
@RestController
@RequestMapping(LINK_CHAT)
@AllArgsConstructor
public class UserController {
    private final SimpMessagingTemplate simpleMessageTemplate;
    private final UserGameDtoService userGameDtoService;
    @MessageMapping("/user")
    public void geoPosSocket(UserGameDto userGameDto) {
        sendMessageToUser(userGameDtoService.updateUser(userGameDto));
    }
    private void sendMessageToUser(List<UserGameDto> userList) {
        // если сообщение отправляется в общий чат

        simpleMessageTemplate.convertAndSend(USER_TOPIC, userList);
    }
}
