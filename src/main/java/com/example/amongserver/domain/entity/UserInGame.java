package com.example.amongserver.domain.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
@ToString
@Table (name = "user_in_game")
public class UserInGame {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uig_id_seq")
    @SequenceGenerator(name = "uig_id_seq", sequenceName = "uig_is_sequence", allocationSize = 1)
    @Column(name = "uig_id")
    private Long id;

    @ToString.Exclude
    @Column(name = "uig_role_id")
    private Role role;

    @ToString.Exclude
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
