package com.example.amongserver.auother.reposirory;

import com.example.amongserver.auother.domain.GameState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/*
репозиторий для GameState
*/
@Repository
public interface GameStateRepository extends JpaRepository<GameState, Long> {
    @Query("SELECT gs FROM GameState gs LEFT JOIN FETCH gs.userLastList WHERE gs.id = :id")
    Optional<GameState> findByIdWithUserList(@Param("id") Long id);
}
