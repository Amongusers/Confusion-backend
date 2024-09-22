package com.example.amongserver.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@SequenceGenerator(name = "id_seq_gen", sequenceName = "room_id_sequence", allocationSize = 1)
@Table(name = "room")
public class Room extends BaseEntityTemp {

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

    // TODO: возможно не стоит изпользовать все операции
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TaskInRoom> taskInRoomSet;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserInGame> userInGameSet;
}
