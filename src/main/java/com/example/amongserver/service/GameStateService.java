package com.example.amongserver.service;

import com.example.amongserver.domain.entity.GameState;
import com.example.amongserver.dto.GameStateDto;

public interface GameStateService {

    // Можно использовать при масштабировании приложения,
    // если добать несколько сессий
    GameState add(GameState gameState);

    // Можно использовать при масштабировании приложения,
    // если добать несколько сессий
    GameState getById(long id);

    // Отправка изменения состояния игры
    // Используется в GameStateController
    // Не используется
    GameStateDto getGameState();
}
