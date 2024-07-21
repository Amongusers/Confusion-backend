package com.example.amongserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
Класс транспортировки данных между клиентом и сервером
Dto нужен для ограничения данных, отправляемых на клиент
и для менее тесной связи клиента с БД
Используется в GeoPositionController
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGeoPositionDto {

    private Long id;

    private double latitude;

    private double longitude;

    private boolean isDead;
}
