package com.example.amongserver.room.repository;

import com.example.amongserver.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r.secretCode FROM Room r")
    List<String> findAllSecretCodes();
}
