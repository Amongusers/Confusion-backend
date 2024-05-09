package com.example.amongserver.mapper;

import com.example.amongserver.domain.entity.User;
import com.example.amongserver.dto.UserVoteDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserVoteMapper {

    public User toUserEntity(UserVoteDto userVoteDto) {


        return User.builder()
                .id(userVoteDto.getId())
                .login(userVoteDto.getLogin())
                .build();
    }

    public UserVoteDto toUserVoteDto(User user) {

        return UserVoteDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .build();
    }
}
