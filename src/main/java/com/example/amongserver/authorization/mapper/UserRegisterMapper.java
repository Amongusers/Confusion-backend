package com.example.amongserver.authorization.mapper;

import com.example.amongserver.authorization.domain.User;
import com.example.amongserver.authorization.dto.UserRegisterRequestDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserRegisterMapper {

    public User toUserEntity(UserRegisterRequestDto userRegisterRequestDto) {

        User user = User.builder()
                .username(userRegisterRequestDto.getUsername())
                .password(userRegisterRequestDto.getPassword())
                .email(userRegisterRequestDto.getEmail())
                .build();

        if (userRegisterRequestDto.getId() != null) user.setId(userRegisterRequestDto.getId());

        return user;
    }
}
