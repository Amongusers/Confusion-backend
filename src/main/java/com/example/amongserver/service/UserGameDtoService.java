package com.example.amongserver.service;

import com.example.amongserver.dto.UserGameDto;

import java.util.List;

public interface UserGameDtoService {
   // public GameStateDto getGameStateDto();
    UserGameDto add(UserGameDto user);
    List<UserGameDto> getAll();
    UserGameDto getById(long id);
    List<UserGameDto> addAll (List<UserGameDto> userList);
    UserGameDto update(long id, UserGameDto userGameDto);
}
