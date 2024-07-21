package com.example.amongserver.reposirory;

import com.example.amongserver.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/*
репозиторий для User
*/
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByIsDead(boolean isDead);
}
