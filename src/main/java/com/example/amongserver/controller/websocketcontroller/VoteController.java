package com.example.amongserver.controller.websocketcontroller;

import com.example.amongserver.dto.UserVoteDto;
import com.example.amongserver.observer.UserVoteDtoObserver;
import com.example.amongserver.service.UserVoteDtoService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static com.example.amongserver.constant.Const.LINK_CHAT;
import static com.example.amongserver.constant.Const.VOTE_TOPIC;
/*
WebSockets контроллер
Используется для голосования
*/
@RestController
@RequestMapping(LINK_CHAT)
@AllArgsConstructor
public class VoteController implements UserVoteDtoObserver {
    private final SimpMessagingTemplate simpleMessageTemplate;
    private final UserVoteDtoService userVoteDtoService;
    @MessageMapping("/vote")
    public void geoPosSocket(UserVoteDto userVoteDto) {
        userVoteDtoService.vote(userVoteDto);
    }


    private void sendMessageToUserVote(UserVoteDto userVoteDto) {
        // если сообщение отправляется в общий чат
        userVoteDtoService.setVoteCanceled(false);
        userVoteDtoService.resetNumberVotes();
        simpleMessageTemplate.convertAndSend(VOTE_TOPIC, userVoteDto);
    }

    @Override
    public void update(UserVoteDto userVoteDto) {
        sendMessageToUserVote(userVoteDto);
    }

    @PostConstruct
    public void init() {
        // Регистрируем VoteController как наблюдателя в UserVoteDtoService
        userVoteDtoService.addObserver(this);
    }
}
