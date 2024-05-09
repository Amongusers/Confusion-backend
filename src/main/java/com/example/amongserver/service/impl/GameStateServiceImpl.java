package com.example.amongserver.service.impl;

import com.example.amongserver.domain.entity.GameState;
import com.example.amongserver.reposirory.GameStateRepository;
import com.example.amongserver.service.GameStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameStateServiceImpl implements GameStateService {
    private final GameStateRepository gemaStateRepository;
    @Override
    public GameState add(GameState gameState) {
        return gemaStateRepository.save(gameState);
    }

    @Override
    public GameState getById(long id) {
        Optional<GameState> gameState = gemaStateRepository.findById(id);
        if(gameState.isEmpty()) throw new RuntimeException("User with ID " + id + " not found");
        return gameState.get();
    }

    @Override
    public GameState update(long id, GameState gameState) {
        Optional<GameState> gameStateOptional = gemaStateRepository.findById(id);
        if (gameStateOptional.isEmpty()) throw new RuntimeException("User with ID " + id + " not found");

        GameState updateGameState  = gameStateOptional.get();
        updateGameState.setGameState(gameState.getGameState());

        return gemaStateRepository.save(updateGameState);
    }
}
