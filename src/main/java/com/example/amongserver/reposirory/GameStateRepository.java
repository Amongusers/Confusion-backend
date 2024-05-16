package com.example.amongserver.reposirory;

import com.example.amongserver.domain.entity.GameState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameStateRepository extends JpaRepository<GameState, Long> {
    @Query("SELECT gs FROM GameState gs LEFT JOIN FETCH gs.userList WHERE gs.id = :id")
    Optional<GameState> findByIdWithUserList(@Param("id") Long id);
}
