package com.example.amongserver.controller.websocketcontroller;

import com.example.amongserver.dto.UserGameDto;
import com.example.amongserver.dto.UserVoteDto;
import com.example.amongserver.service.UserGameDtoService;
import com.example.amongserver.service.UserVoteDtoService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.amongserver.constant.Const.LINK_CHAT;
import static com.example.amongserver.constant.Const.VOTE_TOPIC;

@RestController
@RequestMapping(LINK_CHAT)
@AllArgsConstructor
public class VoteController {
    private final SimpMessagingTemplate simpleMessageTemplate;
    private final UserVoteDtoService userVoteDtoService;
    @MessageMapping("/vote")
    public void geoPosSocket(UserVoteDto userVoteDto) {
        UserVoteDto returnUser = userVoteDtoService.vote(userVoteDto);
        if (returnUser != null) {
            sendMessageToGeoPosition(returnUser);
        } else {
            if (userVoteDtoService.isVoteCanceled()) {
                sendMessageToGeoPosition(null);
            }
        }
    }


    private void sendMessageToGeoPosition(UserVoteDto userVoteDto) {
        // если сообщение отправляется в общий чат
        userVoteDtoService.setVoteCanceled(false);
        simpleMessageTemplate.convertAndSend(VOTE_TOPIC, userVoteDto);
    }
}
