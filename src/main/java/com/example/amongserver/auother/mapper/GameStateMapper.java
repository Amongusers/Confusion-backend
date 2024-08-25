package com.example.amongserver.auother.mapper;

import com.example.amongserver.auother.domain.GameState;
import com.example.amongserver.auother.dto.GameStateDto;
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
