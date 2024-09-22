package com.example.amongserver.domain.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@IdClass(VoteId.class)
@Table (name = "vote")
public class Vote {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_uig_id", nullable = false, foreignKey = @ForeignKey(name = "vote_uig_id_fk"))
    private UserInGame userInGame;  // Ссылка на игрока в игре

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_vt_id", nullable = false, foreignKey = @ForeignKey(name = "vote_vt_id_fk"))
    private Voting voting;  // Ссылка на голосование

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_against_uig_id", nullable = false, foreignKey = @ForeignKey(name = "vote_against_uig_id_fk"))
    private UserInGame votedAgainst;  // Ссылка на игрока, против которого голосовали
}
