package com.example.amongserver.auother.mapper;

import com.example.amongserver.auother.domain.GameCoordinates;
import com.example.amongserver.auother.dto.GameCoordinatesDto;
import lombok.experimental.UtilityClass;
/*
Класс, для конвертации GameCoordinates из Dto к Entity и наооборот
*/
@UtilityClass
public class GameCoordinatesMapper {
    public GameCoordinates toGameCoordinatesEntity(GameCoordinatesDto gameCoordinatesDto) {

        return GameCoordinates.builder()
                .id(gameCoordinatesDto.getId())
                .latitude(gameCoordinatesDto.getLatitude())
                .longitude(gameCoordinatesDto.getLongitude())
                .completed(gameCoordinatesDto.isCompleted())
                .build();
    }



    public GameCoordinatesDto toGameCoordinatesDto(GameCoordinates gameCoordinates) {

        return GameCoordinatesDto.builder()
                .id(gameCoordinates.getId())
                .latitude(gameCoordinates.getLatitude())
                .longitude(gameCoordinates.getLongitude())
                .completed(gameCoordinates.isCompleted())
                .build();
    }
}
