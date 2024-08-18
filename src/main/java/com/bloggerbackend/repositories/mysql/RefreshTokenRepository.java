package com.bloggerbackend.repositories.mysql;

import com.bloggerbackend.entities.Account;
import com.bloggerbackend.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByAccount(Account account);
    boolean existsByToken(String token);
    boolean existsByAccount(Account account);
}
