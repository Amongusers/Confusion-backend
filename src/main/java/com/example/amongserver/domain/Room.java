package com.example.amongserver.domain;

import com.example.amongserver.domain.base.BaseEntityWithId;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "room")
@SequenceGenerator(name = "id_seq_base_with_id", sequenceName = "room_id_sequence", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "room_id"))
public class Room extends BaseEntityWithId {

    @Column (name = "room_latitude", nullable = false, updatable = false)
    private double latitude;

    @Column (name = "room_longitude", nullable = false, updatable = false)
    private double longitude;

    @Column (name = "room_radius", nullable = false, updatable = false)
    private double radius;

    @Column (name = "room_game_state", nullable = false,
            columnDefinition = "integer default 0")
    private int gameState;

    @Column (name = "room_secret_key", nullable = false, updatable = false,
            unique = true, length = 100)
    private String secretCode;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TaskInRoom> taskInRoomSet;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserInGame> userInGameSet;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Voting> votingSet;
}
