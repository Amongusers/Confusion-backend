package com.example.amongserver.mapper;

import com.example.amongserver.domain.entity.User;
import com.example.amongserver.dto.UserRegisterDtoRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserRegisterMapper {

    public User toUserEntity(UserRegisterDtoRequest userRegisterDtoRequest) {

        User user = User.builder()
                .username(userRegisterDtoRequest.getUsername())
                .password(userRegisterDtoRequest.getPassword())
                .email(userRegisterDtoRequest.getEmail())
                .build();

        if (userRegisterDtoRequest.getId() != null) user.setId(userRegisterDtoRequest.getId());

        return user;
    }
}
