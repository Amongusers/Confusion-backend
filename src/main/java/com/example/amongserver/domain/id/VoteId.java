package com.example.amongserver.domain.id;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VoteId implements Serializable {


    private Long userInGameId;


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
