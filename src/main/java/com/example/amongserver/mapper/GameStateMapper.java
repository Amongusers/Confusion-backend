package com.example.amongserver.mapper;

import com.example.amongserver.domain.entity.GameState;
import com.example.amongserver.dto.GameStateDto;
import lombok.experimental.UtilityClass;
/*
Класс, для конвертации GameState из Dto к Entity и наооборот
*/
@UtilityClass
public class GameStateMapper {
    public GameState toGameStateEntity(GameStateDto gameStateDto) {

        return GameState.builder()
                .id(gameStateDto.getId())
                .gameState(gameStateDto.getGameState())
                .build();
    }



    public GameStateDto toGameStateGto(GameState gameState) {

        return GameStateDto.builder()
                .id(gameState.getId())
                .gameState(gameState.getGameState())
                .build();
    }
}
