package com.example.amongserver.service;

import com.example.amongserver.domain.entity.GameCoordinates;
import com.example.amongserver.dto.GameCoordinatesDto;

import java.util.List;

public interface GameCoordinatesService {

    // Получание всех координатат на client в начале игры
    // Используется в StartCoordinatesRestController
    List<GameCoordinates> getAll();


    // Заполнение базы данных изначальными значениями
    void addAll(List<GameCoordinatesDto> gameCoordinatesList);

    // Получение всех невыполненных заданий
    // Используется в GameCoordinatesController
    List<GameCoordinatesDto> getAllIsCompleted(GameCoordinatesDto gameCoordinatesDto);


}
