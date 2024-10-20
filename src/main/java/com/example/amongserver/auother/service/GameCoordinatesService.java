package com.example.amongserver.auother.service;

import com.example.amongserver.auother.dto.GameCoordinatesDto;

import java.util.List;
/*
сервис управления игровыми точками
*/
public interface GameCoordinatesService {

    // Получание всех координатат на client в начале игры
    // Используется в GameCoordinatesRestController
    // url /startCoordinates GET запрос
    List<GameCoordinatesDto> getAll();


    // Заполнение базы данных изначальными значениями
    // Не взаимодействует с клиентом
    void addAll(List<GameCoordinatesDto> gameCoordinatesList);

    // Получение всех невыполненных заданий
    // Используется в GameCoordinatesController
    // url /coordinates WebSockets
    List<GameCoordinatesDto> getAllIsCompleted(GameCoordinatesDto gameCoordinatesDto);


}
