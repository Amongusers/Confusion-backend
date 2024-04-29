package com.example.amongserver.controller.websocketcontroller;

import com.example.amongserver.domain.entity.GameState;
import com.example.amongserver.dto.UserVoteDto;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.amongserver.constant.Const.*;

@RestController
@RequestMapping(LINK_CHAT)
@AllArgsConstructor
public class VoteController {
    private final SimpMessagingTemplate simpleMessageTemplate;
    @MessageMapping("/vote")
    public void geoPosSocket(UserVoteDto userVoteDto) {
        sendMessageToGeoPosition(userVoteDto);
        // отправим сообщения другим пользователям
    }

    private void sendMessageToGeoPosition(UserVoteDto userVoteDto) {
        // если сообщение отправляется в общий чат
        simpleMessageTemplate.convertAndSend(VOTE_TOPIC, userVoteDto);
    }
}
