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
@Table (name = "voting")
public class Voting extends BaseEntityTemp {

    @Column (name = "vt_id")
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vi_room_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "vi_room_id_fk"))
    private Room room;


    @Column(name = "vi_number", nullable = false, updatable = false, unique = true)
    private int number;

    @OneToMany(mappedBy = "voting", fetch = FetchType.EAGER)
    private Set<Vote> votes;
}
