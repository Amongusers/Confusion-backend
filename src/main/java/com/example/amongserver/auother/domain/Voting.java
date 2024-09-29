package com.example.amongserver.auother.domain;

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
@Table (name = "voting")
@SequenceGenerator(name = "id_seq_base_with_id", sequenceName = "vt_id_sequence", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "vt_id"))
public class Voting extends BaseEntityWithId {

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
