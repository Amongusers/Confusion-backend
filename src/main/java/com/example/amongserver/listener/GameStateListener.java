package com.example.amongserver.listener;

import com.example.amongserver.dto.GameStateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.EntityListeners;

import static com.example.amongserver.constant.Const.GEMA_TOPIC;
/*
Класс слушателя, помещённого в БД
Нужен чтобы изменять состояние игры без прямого обращения
Используется в GameState
*/
@EntityListeners(AuditingEntityListener.class)
@Component
@RequiredArgsConstructor
public class GameStateListener implements ApplicationListener<GameStateChangedEvent> {
    private final SimpMessagingTemplate simpleMessageTemplate;



    @Override
    public void onApplicationEvent(GameStateChangedEvent event) {
        GameStateDto gameStateDto = event.getGameStateDto();
        simpleMessageTemplate.convertAndSend(GEMA_TOPIC, gameStateDto);
    }
}
