package com.example.amongserver.authrization.service;

import com.example.amongserver.authrization.dto.UserAuthRequestDto;
import com.example.amongserver.authrization.dto.UserAuthResponseDto;
import com.example.amongserver.registration.domain.User;

public interface UserAuthService {

    // авторизация пользователя
    UserAuthResponseDto authUser(UserAuthRequestDto userAuthRequestDto);

    // поиск юзера по id
//    User getUserById(Long id);
}
