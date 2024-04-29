package com.example.amongserver.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeoPosition {
    private long id;

    private double latitude;

    private double longitude;
    private boolean isDead;

    public GeoPosition(long id, double latitude, double longitude, boolean isDead) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isDead = isDead;
    }

}