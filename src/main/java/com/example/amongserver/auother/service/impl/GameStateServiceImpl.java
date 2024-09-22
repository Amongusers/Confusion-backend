package com.example.amongserver.auother.service.impl;

import com.example.amongserver.auother.domain.GameState;
import com.example.amongserver.auother.dto.GameStateDto;
import com.example.amongserver.auother.mapper.GameStateMapper;
import com.example.amongserver.auother.reposirory.GameCoordinatesRepository;
import com.example.amongserver.auother.reposirory.GameStateRepository;
import com.example.amongserver.auother.service.GameStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameStateServiceImpl implements GameStateService {
    private final GameStateRepository gemaStateRepository;
    private final GameCoordinatesRepository gameCoordinatesRepository;
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
//    @Transactional
    public GameStateDto getGameState() {
        Optional<GameState> gameStateOptional = gemaStateRepository.findById(1L);
        if(gameStateOptional.isEmpty()) throw new RuntimeException("User with ID " + " not found");
        return GameStateMapper.toGameStateGto(gameStateOptional.get());
//        GameState gameState = gemaStateRepository.findByIdWithUserList(1L)
//                .orElseThrow(() -> new RuntimeException("GameState not found"));
//
//        List<User> userList = gameState.getUserList();
//        List<User> userListNotDead = userList.stream()
//                .filter(user -> !user.isDead())
//                .toList();
//
//        boolean isAllReady = userList.stream().allMatch(User::isReady);
//
//        int saveGameState = -1;
//        if (isAllReady) {
//            int totalVotes = userList.stream().mapToInt(User::getNumberVotes).sum();
//            if (totalVotes == 0) {
//                List<GameCoordinates> gameCoordinatesList = gameCoordinatesRepository
//                        .findAll().stream()
//                        .filter(gameCoordinates -> !gameCoordinates.isCompleted())
//                        .toList();
//                long imposterCount = userListNotDead.stream().filter(User::getIsImposter).count();
//                long notImposterCount = userListNotDead.size() - imposterCount;
//
//                if (imposterCount > 0 && notImposterCount > 0) {
//                    saveGameState = 1;
//                } else if ((imposterCount == 0 && notImposterCount > 0) || gameCoordinatesList.isEmpty()) {
//                    saveGameState = 3;
//                } else if ((imposterCount > 0 && notImposterCount == 0)) {
//                    saveGameState = 4;
//                }
//            } else {
//                saveGameState = 2;
//            }
//        } else {
//            saveGameState = 0;
//        }
//        if (saveGameState == -1)
//            throw new RuntimeException();
//
//        gameState.setGameState(saveGameState);
//        return GameStateMapper.toGameStateGto(gemaStateRepository.save(gameState));
    }
}
