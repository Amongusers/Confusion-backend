package com.example.amongserver.service.impl;

import com.example.amongserver.domain.entity.GameCoordinates;
import com.example.amongserver.domain.entity.GameState;
import com.example.amongserver.domain.entity.UserLast;
import com.example.amongserver.dto.GameCoordinatesDto;
import com.example.amongserver.dto.GameStateDto;
import com.example.amongserver.listener.GameStateChangedEvent;
import com.example.amongserver.mapper.GameCoordinatesMapper;
import com.example.amongserver.mapper.GameStateMapper;
import com.example.amongserver.reposirory.GameCoordinatesRepository;
import com.example.amongserver.reposirory.GameStateRepository;
import com.example.amongserver.reposirory.UserLastRepository;
import com.example.amongserver.service.GameCoordinatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameCoordinatesServiceImpl implements GameCoordinatesService {

    private final GameCoordinatesRepository gameCoordinatesRepository;
    private final GameStateRepository gameStateRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final UserLastRepository userLastRepository;

    @Override
    public List<GameCoordinatesDto> getAll() {
        return gameCoordinatesRepository.findAll()
                .stream()
                .map(GameCoordinatesMapper::toGameCoordinatesDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addAll(List<GameCoordinatesDto> gameCoordinatesDtoList) {
        List<GameCoordinates> gameCoordinatesList = gameCoordinatesDtoList
                .stream()
                .map(GameCoordinatesMapper::toGameCoordinatesEntity)
                .collect(Collectors.toList());
        gameCoordinatesRepository.saveAll(gameCoordinatesList);
    }

    @Override
    public List<GameCoordinatesDto> getAllIsCompleted(GameCoordinatesDto gameCoordinatesDto) {
        Long id = gameCoordinatesDto.getId();
        Optional<GameCoordinates> gameCoordinatesOptional = gameCoordinatesRepository.findById(id);
        if(gameCoordinatesOptional.isEmpty()) throw new RuntimeException("User with ID " + id + " not found");

        GameCoordinates gameCoordinatesDB = gameCoordinatesOptional.get();
        GameCoordinates gameCoordinatesClient = GameCoordinatesMapper.toGameCoordinatesEntity(gameCoordinatesDto);
        gameCoordinatesDB.setCompleted(gameCoordinatesClient.isCompleted());
        gameCoordinatesRepository.save(gameCoordinatesDB);
        List<GameCoordinates> gameCoordinatesList = gameCoordinatesRepository.findAllByCompleted(false);


        Optional<GameState> gameStateOptional = gameStateRepository.findById(1L);
        if (gameStateOptional.isPresent()) {
            GameState gameState = gameStateOptional.get();
            if ((gameState.getGameState() == 1 || gameState.getGameState() == 2) && gameCoordinatesList.isEmpty()) {
                gameState.setGameState(3);
                GameStateDto gameStateDto = GameStateMapper.toGameStateGto(gameStateRepository.save(gameState));
                deleteAllUsers(gameState);
                GameStateChangedEvent event = new GameStateChangedEvent(this, gameStateDto);
                eventPublisher.publishEvent(event);
            }
        }
        return gameCoordinatesList
                .stream()
                .map(GameCoordinatesMapper::toGameCoordinatesDto)
                .collect(Collectors.toList());
    }
    // TODO : Нужно будет создать слушателя на GemaState
    private void deleteAllUsers(GameState gameState) {
        for (UserLast userLast : gameState.getUserLastList()) {
            userLastRepository.delete(userLast);
        }
    }

}
