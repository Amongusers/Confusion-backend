package com.example.amongserver.domain;


import com.example.amongserver.domain.id.VoteId;
import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table (name = "vote")
@IdClass(VoteId.class)
public class Vote {

    @Id
    @Column(name = "vote_uig_id", nullable = false)
    private Long userInGameId;

    @Id
    @Column(name = "vote_vt_id", nullable = false)
    private Long votingId;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_uig_id", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "vote_uig_id_fk"))
    private UserInGame userInGame;  // Ссылка на игрока в игре

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_vt_id", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "vote_vt_id_fk"))
    private Voting voting;  // Ссылка на голосование

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_against_uig_id", nullable = false,
            foreignKey = @ForeignKey(name = "vote_against_uig_id_fk"))
    private UserInGame votedAgainst;  // Ссылка на игрока, против которого голосовали
}
