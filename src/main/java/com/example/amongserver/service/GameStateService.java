package com.example.amongserver.service;

import com.example.amongserver.domain.entity.GameState;

public interface GameStateService {

    GameState add(GameState gameState);
    GameState getById(long id);
    GameState update(long id, GameState user);
}
