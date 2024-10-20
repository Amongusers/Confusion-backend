package com.example.amongserver.auother.service;

import com.example.amongserver.auother.domain.GameState;
import com.example.amongserver.auother.dto.GameStateDto;

/*
сервис состояния игры
*/
public interface GameStateService {

    // Можно использовать при масштабировании приложения,
    // если добать несколько сессий
    GameState add(GameState gameState);

    // Можно использовать при масштабировании приложения,
    // если добать несколько сессий
    GameState getById(long id);

    // Отправка изменения состояния игры
    // Используется в GameStateController (не используется)
    // Используется в GameStateRestController для проверки состояния игры
    // url /gameStateStart GET запрос
    GameStateDto getGameState();
}
