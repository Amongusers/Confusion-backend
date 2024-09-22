package com.example.amongserver.domain.entity;

import lombok.*;

import javax.persistence.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_id_seq")
    @SequenceGenerator(name = "room_id_seq", sequenceName = "room_id_sequence", allocationSize = 1)
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
