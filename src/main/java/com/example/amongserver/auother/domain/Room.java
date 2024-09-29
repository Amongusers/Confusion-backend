package com.example.amongserver.auother.domain;

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
@SequenceGenerator(name = "id_seq_base_temp", sequenceName = "room_id_sequence", allocationSize = 1)
@Table(name = "room")
@AttributeOverride(name = "id", column = @Column(name = "room_id"))
public class Room extends BaseEntityTemp {

    @Column (name = "room_latitude", nullable = false, updatable = false)
    private double latitude;

    @Column (name = "room_longitude", nullable = false, updatable = false)
    private double longitude;

    @Column (name = "room_radius", nullable = false, updatable = false)
    private double radius;

    @Column (name = "room_game_state", nullable = false,
            columnDefinition = "integer default 0")
    private int gameState;

    // TODO: возможно не стоит изпользовать все операции
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TaskInRoom> taskInRoomSet;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserInGame> userInGameSet;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private Set<Voting> votingSet;
}
