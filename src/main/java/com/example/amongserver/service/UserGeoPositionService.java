package com.example.amongserver.service;

import com.example.amongserver.dto.UserGeoPositionDto;

/*
сервис местоположения полользователей
*/
public interface UserGeoPositionService {


    // Обновление данных местоположения
    // Используется в GeoPositionController
    // url /sock WebSockets
    UserGeoPositionDto updateGeoPosition(UserGeoPositionDto userGeoPositionDto);
}
