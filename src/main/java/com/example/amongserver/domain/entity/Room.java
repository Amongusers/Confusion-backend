package com.example.amongserver.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @Column (name = "room_latitude")
    private double latitude;

    @Column (name = "room_longitude")
    private double longitude;

    @Column (name = "room_radius")
    private double radius;

    @Column (name = "room_game_state")
    private int gameState;
}
