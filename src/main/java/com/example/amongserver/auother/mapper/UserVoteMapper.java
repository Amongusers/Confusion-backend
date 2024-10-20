package com.example.amongserver.auother.mapper;

import com.example.amongserver.auother.domain.UserLast;
import com.example.amongserver.auother.dto.UserVoteDto;
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
