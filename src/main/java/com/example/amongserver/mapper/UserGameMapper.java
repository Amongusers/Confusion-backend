package com.example.amongserver.mapper;

import com.example.amongserver.domain.entity.UserLast;
import com.example.amongserver.dto.UserGameDto;
import lombok.experimental.UtilityClass;
/*
Класс, для конвертации User из UserGameDto к Entity и наооборот
*/
@UtilityClass
public class UserGameMapper {
    public UserLast toUserEntity(UserGameDto userGameDto) {

        UserLast userLast = UserLast.builder()
                .login(userGameDto.getLogin())
                .isReady(userGameDto.isReady())
                .isImposter(userGameDto.getIsImposter())
                .build();
        if (userGameDto.getId()!=null) {
            userLast.setId(userGameDto.getId());
        }
        return userLast;
    }



    public UserGameDto toUserGameDto(UserLast userLast) {

        return UserGameDto.builder()
                .id(userLast.getId())
                .login(userLast.getLogin())
                .isReady(userLast.isReady())
                .isImposter(userLast.getIsImposter())
                .build();
    }


}
