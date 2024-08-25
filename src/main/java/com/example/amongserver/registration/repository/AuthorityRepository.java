package com.example.amongserver.registration.repository;

import com.example.amongserver.registration.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByAuthority (String authority);
}
