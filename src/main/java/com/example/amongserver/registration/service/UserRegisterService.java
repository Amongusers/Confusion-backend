package com.example.amongserver.registration.service;

import com.example.amongserver.registration.dto.UserRegisterRequestDto;

public interface UserRegisterService {
    // регестрация пользователя
    // используется в UserRestController
    // url /user POST запрос
    void saveUser(UserRegisterRequestDto userRegisterRequestDto);
}
