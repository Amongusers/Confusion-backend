package com.example.amongserver.service.impl;

import com.example.amongserver.domain.entity.GameState;
import com.example.amongserver.domain.entity.User;
import com.example.amongserver.dto.GameStateDto;
import com.example.amongserver.mapper.GameStateMapper;
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


    // TODO : нужно улучшить
    @Override
    public GameStateDto getGameState() {
        GameState gameState = gemaStateRepository.getById(1L);
        boolean isAllReady = true;
        int saveGameState;
        for (User user : gameState.getUserList()) {
            if (!user.isReady()) {
                isAllReady = false;
                break;
            }
        }
        if (isAllReady) {
            int totalVotes = gameState.getUserList()
                    .stream().mapToInt(User::getNumberVotes).sum();
            if (totalVotes==0) {
                saveGameState = 1;
            } else {
                saveGameState = 2;
            }
        } else {
            saveGameState = 0;
        }
        gameState.setGameState(saveGameState);

        return GameStateMapper.toGameStateGto(gemaStateRepository.save(gameState));
    }
}
