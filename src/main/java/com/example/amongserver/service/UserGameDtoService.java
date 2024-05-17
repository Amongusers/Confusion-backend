package com.example.amongserver.service;

import com.example.amongserver.dto.UserGameDto;

import java.util.List;

public interface UserGameDtoService {

    // регестрация пользователя
    // используется в UserRestController
    UserGameDto add(UserGameDto user);


    // получение списка всех пользователей
    // не используется
    List<UserGameDto> getAll();




    // Обновление данных пользователя
    // Используется в UserController
    // До начала игры

    List<UserGameDto> updateUser (UserGameDto userGameDto);


}
