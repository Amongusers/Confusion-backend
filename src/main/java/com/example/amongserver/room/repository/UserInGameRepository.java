package com.example.amongserver.room.repository;

import com.example.amongserver.domain.UserInGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInGameRepository extends JpaRepository<UserInGame, Long> {
}
