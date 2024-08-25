package com.example.amongserver.auother.listener;

import com.example.amongserver.auother.dto.GameStateDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
/*
Класс, служающий для создания слушателя GameStateListener
*/
@Getter
public class GameStateChangedEvent extends ApplicationEvent {
    private final GameStateDto gameStateDto;

    public GameStateChangedEvent(Object source, GameStateDto gameStateDto) {
        super(source);
        this.gameStateDto = gameStateDto;
    }

}
