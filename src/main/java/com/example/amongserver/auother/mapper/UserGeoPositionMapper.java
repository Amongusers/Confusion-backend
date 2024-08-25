package com.example.amongserver.auother.mapper;

import com.example.amongserver.auother.domain.UserLast;
import com.example.amongserver.auother.dto.UserGeoPositionDto;
import lombok.experimental.UtilityClass;
/*
Класс, для конвертации User из UserGeoPositionDto к Entity и наооборот
*/
@UtilityClass
public class UserGeoPositionMapper {
    public UserLast toUserEntity(UserGeoPositionDto userGeoPositionDto) {

        return UserLast.builder()
                .id(userGeoPositionDto.getId())
                .latitude(userGeoPositionDto.getLatitude())
                .longitude(userGeoPositionDto.getLongitude())
                .isDead(userGeoPositionDto.isDead())
                .build();
    }



    public UserGeoPositionDto toUserGeoPositionGto(UserLast userLast) {

        return UserGeoPositionDto.builder()
                .id(userLast.getId())
                .latitude(userLast.getLatitude())
                .longitude(userLast.getLongitude())
                .isDead(userLast.isDead())
                .build();
    }

}
