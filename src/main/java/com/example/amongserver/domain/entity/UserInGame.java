package com.example.amongserver.domain.entity;

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
@SequenceGenerator(name = "id_seq_gen", sequenceName = "uig_id_sequence", allocationSize = 1)
@Table (name = "user_in_game",
        indexes = {
                @Index(name = "idx_uig_role_id_fk", columnList = "uig_role_id"),
                @Index(name = "idx_uig_room_id_fk", columnList = "uig_room_id")
        })
public class UserInGame extends BaseEntityTemp {

    @Column(name = "uig_id")
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uig_role_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "uig_role_id_fk"))
    private Role role;

    @ToString.Exclude
    @JoinColumn(name = "uig_room_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "uig_room_id_fk"))
    @Column(name = "uig_room_id")
    private Room room;

    @Column (name = "uig_latitude", nullable = false)
    private double latitude;

    @Column (name = "uig_longitude", nullable = false)
    private double longitude;

    @Column (name = "uig_is_dead", nullable = false,
            columnDefinition = "boolean default false")
    private boolean isDead;

    // TODO: надо добавить enum
    @Column (name = "uig_color", length = 10)
    private String color;

    @OneToMany(mappedBy = "userInGame", fetch = FetchType.LAZY)
    private Set<Vote> voteSet;

    @OneToMany(mappedBy = "votedAgainst", fetch = FetchType.LAZY)
    private Set<Vote> voteAgainstSet;
}
