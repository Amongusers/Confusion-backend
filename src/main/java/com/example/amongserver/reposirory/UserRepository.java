package com.example.amongserver.reposirory;

import com.example.amongserver.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNumberVotesGreaterThan(int numberVotes);
}
