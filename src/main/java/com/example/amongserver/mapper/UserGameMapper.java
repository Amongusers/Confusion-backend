package com.example.amongserver.mapper;

import com.example.amongserver.domain.entity.User;
import com.example.amongserver.dto.UserGameDto;
import lombok.experimental.UtilityClass;
/*
Класс, для конвертации User из UserGameDto к Entity и наооборот
*/
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



    public UserGameDto toUserGameDto(User user) {

        return UserGameDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .isReady(user.isReady())
                .isImposter(user.getIsImposter())
                .build();
    }


}
