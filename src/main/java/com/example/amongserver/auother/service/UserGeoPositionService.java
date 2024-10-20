package com.example.amongserver.auother.service;

import com.example.amongserver.auother.dto.UserGeoPositionDto;

import java.util.List;

/*
сервис местоположения полользователей
*/
public interface UserGeoPositionService {


    // Обновление данных местоположения
    // Используется в GeoPositionController
    // url /sock WebSockets
    List<UserGeoPositionDto> updateGeoPosition(UserGeoPositionDto userGeoPositionDto);
}
