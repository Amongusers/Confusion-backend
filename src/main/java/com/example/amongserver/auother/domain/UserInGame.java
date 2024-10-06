package com.example.amongserver.auother.domain;

import com.example.amongserver.registration.domain.User;
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
@Table (name = "user_in_game",
        indexes = {
                @Index(name = "idx_uig_role_id_fk", columnList = "uig_role_id"),
                @Index(name = "idx_uig_room_id_fk", columnList = "uig_room_id")
        })
@SequenceGenerator(name = "id_seq_base_with_id", sequenceName = "uig_id_sequence", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "uig_id"))
public class UserInGame extends BaseEntityWithId {

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uig_user_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "uig_user_id_fk"))
    private User user;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uig_role_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "uig_role_id_fk"))
    private Role role;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uig_room_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "uig_room_id_fk"))
    private Room room;

    @Column(name = "uig_is_admin", nullable = false)
    private byte isAdmin;

    @Column(name = "uig_connected_status", nullable = false)
    private byte connectedStatus;

    @Column (name = "uig_latitude", nullable = false)
    private double latitude;

    @Column (name = "uig_longitude", nullable = false)
    private double longitude;

    @Column (name = "uig_is_dead", nullable = false,
            columnDefinition = "boolean default false")
    private boolean isDead;

    @Column (name = "uig_color", length = 100, nullable = false)
    private String color;

    @OneToMany(mappedBy = "userInGame", fetch = FetchType.LAZY)
    private Set<Vote> voteSet;

    @OneToMany(mappedBy = "votedAgainst", fetch = FetchType.LAZY)
    private Set<Vote> voteAgainstSet;
}
