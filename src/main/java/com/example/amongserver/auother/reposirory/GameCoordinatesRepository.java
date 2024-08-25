package com.example.amongserver.auother.reposirory;

import com.example.amongserver.auother.domain.GameCoordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/*
репозиторий для GameCoordinates
*/
@Repository
public interface GameCoordinatesRepository extends JpaRepository<GameCoordinates, Long> {
    List<GameCoordinates> findAllByCompleted(boolean isCompleted);
}
