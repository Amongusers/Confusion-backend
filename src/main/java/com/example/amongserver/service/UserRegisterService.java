package com.example.amongserver.service;

import com.example.amongserver.dto.UserGameDto;
import com.example.amongserver.dto.UserRegisterDto;

public interface UserRegisterService {
    // регестрация пользователя
    // используется в UserRestController
    // url /user POST запрос
    void add(UserRegisterDto userRegisterDto);
}
