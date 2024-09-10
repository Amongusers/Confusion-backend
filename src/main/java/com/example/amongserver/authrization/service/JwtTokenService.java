package com.example.amongserver.authrization.service;

import com.example.amongserver.authrization.dto.UserAuthRequestDto;
import com.example.amongserver.authrization.dto.UserAuthResponseDto;

public interface JwtTokenService {

    // авторизация пользователя
    UserAuthResponseDto authUser(UserAuthRequestDto userAuthRequestDto);
}
