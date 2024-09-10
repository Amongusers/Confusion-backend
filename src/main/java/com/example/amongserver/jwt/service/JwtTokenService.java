package com.example.amongserver.jwt.service;

import com.example.amongserver.jwt.dto.UserAuthRequestDto;
import com.example.amongserver.jwt.dto.UserAuthResponseDto;

public interface JwtTokenService {

    // авторизация пользователя
    UserAuthResponseDto authUser(UserAuthRequestDto userAuthRequestDto);
}
