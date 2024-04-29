package com.example.amongserver.service;

import com.example.amongserver.domain.entity.User;

import java.util.List;

public interface UserService {
   // public GameStateDto getGameStateDto();
    User add(User user);
    List<User> getAll();
    User getById(long id);
    List<User> addAll (List<User> userList);
}
