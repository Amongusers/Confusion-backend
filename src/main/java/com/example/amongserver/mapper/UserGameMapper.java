package com.example.amongserver.mapper;

import com.example.amongserver.domain.entity.User;
import com.example.amongserver.dto.UserGameDto;
import com.example.amongserver.dto.UserVoteDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserGameMapper {
    public User toUserEntity(UserGameDto userGameDto) {

        User user = User.builder()
                .login(userGameDto.getLogin())
                .isReady(userGameDto.isReady())
                .isImposter(userGameDto.getIsImposter())
                .build();
        if (userGameDto.getId()!=null) {
            user.setId(userGameDto.getId());
        }
        return user;
    }



    public UserGameDto toUserGameGto(User user) {

        return UserGameDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .isReady(user.isReady())
                .isImposter(user.getIsImposter())
                .build();
    }


}
