package com.example.amongserver.mapper;

import com.example.amongserver.domain.entity.User;
import com.example.amongserver.dto.UserVoteDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserVoteMapper {

    public User toUserEntity(UserVoteDto userVoteDto) {

        User user = User.builder()
                .numberVotes(userVoteDto.getNumberVotes())
                .build();

        if (userVoteDto.getId()!=null) {
            user.setId(userVoteDto.getId());
        }

        return user;
    }

    public UserVoteDto toUserVoteDto(User user) {

        return UserVoteDto.builder()
                .numberVotes(user.getNumberVotes())
                .build();
    }
}
