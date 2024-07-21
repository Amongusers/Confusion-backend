package com.example.amongserver.mapper;

import com.example.amongserver.domain.entity.User;
import com.example.amongserver.dto.UserVoteDto;
import lombok.experimental.UtilityClass;
/*
Класс, для конвертации User из VoteDto к Entity и наооборот
*/
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
