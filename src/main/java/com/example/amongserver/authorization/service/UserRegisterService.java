package com.example.amongserver.authorization.service;

import com.example.amongserver.authorization.dto.UserProfileDto;
import com.example.amongserver.authorization.dto.UserRegisterRequestDto;

public interface UserRegisterService {
    // регестрация пользователя
    // используется в UserRestController
    // url /user POST запрос
    void add(UserRegisterRequestDto userRegisterRequestDto);
}
