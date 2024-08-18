package com.bloggerbackend.repositories.mysql;

import com.bloggerbackend.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
    Optional<Account> findByUsername(String username);
    boolean existsByEmail (String email);
    boolean existsByMobile (String mobile);
    Optional<Account> findByVerificationToken(String email);
    boolean existsByVerificationToken(String token);
}
