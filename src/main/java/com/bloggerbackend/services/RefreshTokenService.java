package com.bloggerbackend.services;

import com.bloggerbackend.entities.Account;
import com.bloggerbackend.entities.RefreshToken;
import com.bloggerbackend.exceptions.EntityNotFoundException;
import com.bloggerbackend.repositories.mysql.AccountRepository;
import com.bloggerbackend.repositories.mysql.RefreshTokenRepository;
import com.bloggerbackend.utils.JwtUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtils jwtUtils;

    public RefreshTokenService(AccountRepository accountRepository, RefreshTokenRepository refreshTokenRepository, JwtUtils jwtUtils) {
        this.accountRepository = accountRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtUtils = jwtUtils;
    }

    public RefreshToken createRefreshToken(Account account){
        if(refreshTokenRepository.existsByAccount(account)) {
            RefreshToken tokenSaved = refreshTokenRepository.findByAccount(account).get();
            refreshTokenRepository.delete(tokenSaved);
        }
        RefreshToken refreshToken = RefreshToken.builder()
                .account(accountRepository.findByEmail(account.getEmail()).orElseThrow())
                .token(UUID.randomUUID().toString())
                .expiryDate(LocalDateTime.now().plusDays(1))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken findByToken(String token){
        return refreshTokenRepository.findByToken(token).orElseThrow(() -> new EntityNotFoundException("Refresh token not found"));
    }

    public boolean isExpired(RefreshToken token){
        if(token.getExpiryDate().isBefore(LocalDateTime.now())){
            refreshTokenRepository.delete(token);
            return false;
        }
        return true;
    }
    public RefreshToken getRefreshTokenByAccessToken(String accessToken){
        String email = jwtUtils.extractUsername(accessToken);
        Account account = accountRepository.findByEmail(email).orElseThrow();
        return refreshTokenRepository.findByAccount(account).orElseThrow();
    }
}
