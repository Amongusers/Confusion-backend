package com.example.amongserver.authorization.service;

import com.example.amongserver.authorization.dto.UserLoginRequestDto;

public interface UserAuthService {

    public String getToken(UserLoginRequestDto userLoginRequestDto);
}
