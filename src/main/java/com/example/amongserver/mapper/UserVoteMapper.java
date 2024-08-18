package com.example.amongserver.mapper;

import com.example.amongserver.domain.entity.UserLast;
import com.example.amongserver.dto.UserVoteDto;
import lombok.experimental.UtilityClass;
/*
Класс, для конвертации User из VoteDto к Entity и наооборот
*/
@UtilityClass
public class UserVoteMapper {

    public UserLast toUserEntity(UserVoteDto userVoteDto) {


        return UserLast.builder()
                .id(userVoteDto.getId())
                .login(userVoteDto.getLogin())
                .build();
    }

    public UserVoteDto toUserVoteDto(UserLast userLast) {

        return UserVoteDto.builder()
                .id(userLast.getId())
                .login(userLast.getLogin())
                .build();
    }
}
