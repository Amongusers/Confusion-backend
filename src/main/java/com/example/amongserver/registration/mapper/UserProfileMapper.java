package com.example.amongserver.registration.mapper;

import com.example.amongserver.registration.domain.User;
import com.example.amongserver.registration.dto.UserProfileDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserProfileMapper {
    public UserProfileDto toUserProfileDto(User user) {

        return UserProfileDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getPassword())
                .build();
    }

    public User toUserEntity(UserProfileDto userProfileDto) {

        User user = User.builder()
                .username(userProfileDto.getUsername())
                .email(userProfileDto.getEmail())
                .build();

        if (userProfileDto.getId() != null) user.setId(userProfileDto.getId());

        return user;
    }
}
