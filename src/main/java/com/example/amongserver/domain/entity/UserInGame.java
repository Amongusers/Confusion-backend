package com.example.amongserver.domain.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class UserInGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uig_id")
    private Long id;

    @Column(name = "uig_role_id")
    private Role role;

    @Column(name = "uig_room_id")
    private Room room;

    @Column (name = "uig_latitude")
    private double latitude;

    @Column (name = "uig_longitude")
    private double longitude;

    @Column (name = "uig_is_dead")
    private boolean isDead;

    @Column (name = "uig_color")
    private String color;
}
