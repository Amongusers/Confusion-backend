package com.example.amongserver.mapper;

import com.example.amongserver.domain.entity.User;
import com.example.amongserver.dto.UserGeoPositionDto;
import lombok.experimental.UtilityClass;
/*
Класс, для конвертации User из UserGeoPositionDto к Entity и наооборот
*/
@UtilityClass
public class UserGeoPositionMapper {
    public User toUserEntity(UserGeoPositionDto userGeoPositionDto) {

        return User.builder()
                .id(userGeoPositionDto.getId())
                .latitude(userGeoPositionDto.getLatitude())
                .longitude(userGeoPositionDto.getLongitude())
                .isDead(userGeoPositionDto.isDead())
                .build();
    }



    public UserGeoPositionDto toUserGeoPositionGto(User user) {

        return UserGeoPositionDto.builder()
                .id(user.getId())
                .latitude(user.getLatitude())
                .longitude(user.getLongitude())
                .isDead(user.isDead())
                .build();
    }

}
