package com.example.amongserver.registration.mapper;

import com.example.amongserver.domain.User;
import com.example.amongserver.registration.controller.dto.UserRegisterRequestDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserRegisterMapper {

    public User toUserEntity(UserRegisterRequestDto userRegisterRequestDto) {

        return User.builder()
                .username(userRegisterRequestDto.getUsername())
                .password(userRegisterRequestDto.getPassword())
                .email(userRegisterRequestDto.getEmail())
                .build();
    }
}
