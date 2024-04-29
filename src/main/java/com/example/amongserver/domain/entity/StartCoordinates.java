package com.example.amongserver.domain.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "start_coordinates")
public class StartCoordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "latitude")
    private double latitude;

    @Column (name = "longitude")
    private double longitude;

    @Column (name = "completed")
    private boolean completed;

    public StartCoordinates(double latitude, double longitude, boolean completed) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.completed = completed;
    }
}
