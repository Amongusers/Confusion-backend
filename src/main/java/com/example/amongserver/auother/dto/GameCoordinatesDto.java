package com.example.amongserver.auother.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
Класс транспортировки данных между клиентом и сервером
Dto нужен для ограничения данных, отправляемых на клиент
и для менее тесной связи клиента с БД
Используется в GameCoordinatesRestController и GameCoordinatesController
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameCoordinatesDto {


    private Long id;


    private double latitude;


    private double longitude;


    private boolean completed;

    public GameCoordinatesDto(double latitude, double longitude, boolean completed) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.completed = completed;
    }
}
