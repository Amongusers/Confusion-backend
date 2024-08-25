package com.example.amongserver.service;

import com.example.amongserver.dto.UserProfileDto;
import com.example.amongserver.dto.UserRegisterDtoRequest;

public interface UserRegisterService {
    // регестрация пользователя
    // используется в UserRestController
    // url /user POST запрос
    UserProfileDto add(UserRegisterDtoRequest userRegisterDtoRequest);
}
