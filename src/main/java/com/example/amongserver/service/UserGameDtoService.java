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


    // получение списка всех живых пользователей
    // используется в UserRestController
    List<UserGameDto> getAllIsDead();

    // Поиск пользователя по id
    // используется в UserController
    UserGameDto getById(long id);


    List<UserGameDto> addAll (List<UserGameDto> userList);
    UserGameDto update(long id, UserGameDto userGameDto);
    UserGameDto vote(UserGameDto userGameDto);

    boolean isVoteCanceled();
}
