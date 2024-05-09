package com.example.amongserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
