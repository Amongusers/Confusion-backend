package com.example.amongserver.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
/*
Entity класс GameCoordinates
Сущность, которая хранится в БД
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "start_coordinates")
public class GameCoordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "latitude")
    private double latitude;

    @Column (name = "longitude")
    private double longitude;

    @Column (name = "completed")
    private boolean completed;

    public GameCoordinates(double latitude, double longitude, boolean completed) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.completed = completed;
    }
}
