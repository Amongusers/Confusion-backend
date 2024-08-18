package com.example.amongserver.reposirory;

import com.example.amongserver.domain.entity.UserLast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/*
репозиторий для User
*/
@Repository
public interface UserLastRepository extends JpaRepository<UserLast, Long> {
    List<UserLast> findAllByIsDead(boolean isDead);
}
