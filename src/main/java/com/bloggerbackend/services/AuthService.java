package com.bloggerbackend.services;
import com.bloggerbackend.constants.ApiHeaders;
import com.bloggerbackend.entities.Account;
import com.bloggerbackend.entities.Author;
import com.bloggerbackend.entities.RefreshToken;
import com.bloggerbackend.enums.AccountStatus;
import com.bloggerbackend.mappers.AccountMapper;
import com.bloggerbackend.mappers.AuthorMapper;
import com.bloggerbackend.models.request.AccountReq;
import com.bloggerbackend.models.request.LoginReq;
import com.bloggerbackend.models.response.AccountRes;
import com.bloggerbackend.models.response.LoginRes;
import com.bloggerbackend.models.response.RefreshTokenRes;
import com.bloggerbackend.repositories.mysql.AccountRepository;
import com.bloggerbackend.repositories.mysql.AuthorRepository;
import com.bloggerbackend.repositories.mysql.RefreshTokenRepository;
import com.bloggerbackend.utils.JwtUtils;
import com.bloggerbackend.validators.AuthValidator;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final AccountMapper accountMapper;
    private final RefreshTokenService refreshTokenService;
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final AuthValidator accountValidator;
    private final AuthValidator authValidator;
    private final EmailService emailService;

    public AccountRes register(AccountReq request) {
        accountValidator.validateRegister(request);
        Author author = authorRepository.save(authorMapper.mapToAuthor(request));
        Account account = accountMapper.mapToAccount(request, false);
        account.setAuthor(author);
        accountRepository.save(account);
//        String confirmationUrl = "http://localhost:8090/api/v1/verify-email?token=" + account.getVerificationToken();
//        emailService.sendEmail(account.getEmail(), "Email Verification", "Click the link to verify your email: " + confirmationUrl);
        return accountMapper.mapToAccountRes(account);
    }

    public LoginRes login (LoginReq request) {
        authValidator.validateLogin(request);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var account = accountRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtUtils.generateToken(account);
        var refreshToken = refreshTokenService.createRefreshToken(account).getToken();
        return LoginRes.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public RefreshTokenRes refreshToken(HttpServletRequest request) throws IOException {
        String token = request.getHeader(ApiHeaders.refreshToken);
        authValidator.validateRefreshToken(token);
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).get();
        String accessToken = jwtUtils.generateToken(refreshToken.getAccount());
        return RefreshTokenRes.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    public String validateAccount(String token) {
        boolean isActive = accountRepository.existsByVerificationToken(token);
        if (isActive) {
            Account account = accountRepository.findByVerificationToken(token).get();
            account.setStatus(AccountStatus.ACTIVE);
            return "Account verified!";
        }
        return "Error";
    }
}