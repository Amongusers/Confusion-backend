package com.example.amongserver.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

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

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }


    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean getIsDead() {
        return isDead;
    }
    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }
}