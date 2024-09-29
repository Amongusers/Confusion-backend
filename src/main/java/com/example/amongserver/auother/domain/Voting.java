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
@SequenceGenerator(name = "id_seq_base_temp", sequenceName = "vt_id_sequence", allocationSize = 1)
@Table (name = "voting")
@AttributeOverride(name = "id", column = @Column(name = "vt_id"))
public class Voting extends BaseEntityTemp {

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
