package com.example.amongserver.service.impl;

import com.example.amongserver.dto.UserGeoPositionDto;

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
