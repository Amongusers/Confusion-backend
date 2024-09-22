package com.example.amongserver.authorization.service;

import com.example.amongserver.authorization.dto.UserAuthRequestDto;
import com.example.amongserver.authorization.dto.UserAuthResponseDto;

public interface UserAuthService {

    // авторизация пользователя
    UserAuthResponseDto authUser(UserAuthRequestDto userAuthRequestDto);

    // поиск юзера по id
//    User getUserById(Long id);
}
