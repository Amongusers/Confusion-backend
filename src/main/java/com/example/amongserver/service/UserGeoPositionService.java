package com.example.amongserver.service;

import com.example.amongserver.dto.UserGeoPositionDto;

public interface UserGeoPositionService {


    // Обновление данных местоположения
    // Используется в GeoPositionController
    UserGeoPositionDto updateGeoPosition(UserGeoPositionDto userGeoPositionDto);
}
