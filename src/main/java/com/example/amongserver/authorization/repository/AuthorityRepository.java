package com.example.amongserver.authorization.repository;

import com.example.amongserver.authorization.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByAuthority (String authority);
}
