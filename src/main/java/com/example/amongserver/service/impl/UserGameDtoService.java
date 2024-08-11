package com.example.amongserver.service.impl;

import com.example.amongserver.dto.UserGameDto;
import com.example.amongserver.dto.UserKillDtoRequest;

import java.util.List;

/*
основной сервис приложения, используется во время всей игры
*/

public interface UserGameDtoService {
    // TODO : в двух контроллерах одинаковый url, нужно исправить
    // регестрация пользователя
    // используется в UserRestController
    // url /user POST запрос
    UserGameDto add(UserGameDto user);


    // получение списка всех пользователей
    // не используется
    List<UserGameDto> getAll();




    // Обновление данных пользователя
    // Используется в UserController
    // До начала игры
    // url /user WebSockets
    List<UserGameDto> updateUser (UserGameDto userGameDto);


    // Убийство импостером
    // Используется в UserRestController
    void killUser (UserKillDtoRequest userKillDtoRequest);


}
