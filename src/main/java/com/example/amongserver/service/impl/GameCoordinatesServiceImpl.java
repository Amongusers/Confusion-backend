package com.example.amongserver.service.impl;

import com.example.amongserver.domain.entity.GameCoordinates;
import com.example.amongserver.dto.GameCoordinatesDto;
import com.example.amongserver.mapper.GameCoordinatesMapper;
import com.example.amongserver.reposirory.GameCoordinatesRepository;
import com.example.amongserver.service.GameCoordinatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameCoordinatesServiceImpl implements GameCoordinatesService {

    private final GameCoordinatesRepository gameCoordinatesRepository;

    @Override
    public List<GameCoordinates> getAll() {
        return gameCoordinatesRepository.findAll();
    }

    @Override
    public void add(GameCoordinates startCoordinates) {
        gameCoordinatesRepository.save(startCoordinates);
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
        List<GameCoordinates> gameCoordinatesList = gameCoordinatesRepository.findAllByIsCompleted(false);
        return gameCoordinatesList
                .stream()
                .map(GameCoordinatesMapper::toGameCoordinatesDto)
                .collect(Collectors.toList());
    }


}
