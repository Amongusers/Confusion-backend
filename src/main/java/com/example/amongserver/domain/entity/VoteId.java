package com.example.amongserver.domain.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VoteId implements Serializable {

    @ToString.Exclude
    private Long userInGameId;

    @ToString.Exclude
    private Long votingId;

    // TODO: допилить методы
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteId voteId = (VoteId) o;
        return Objects.equals(userInGameId, voteId.getUserInGameId()) &&
                Objects.equals(votingId, voteId.getVotingId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userInGameId, votingId);
    }
}
