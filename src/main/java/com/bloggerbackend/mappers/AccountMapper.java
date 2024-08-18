package com.bloggerbackend.mappers;

import com.bloggerbackend.entities.Account;
import com.bloggerbackend.enums.AccountStatus;
import com.bloggerbackend.enums.Role;
import com.bloggerbackend.models.request.AccountReq;
import com.bloggerbackend.models.response.AccountRes;
import com.bloggerbackend.models.response.AuthorRes;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AccountMapper {
    private final AuthorMapper authorMapper;
    private final PasswordEncoder passwordEncoder;

    public AccountMapper(AuthorMapper authorMapper, PasswordEncoder passwordEncoder) {
        this.authorMapper = authorMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Account mapToAccount(AccountReq from, boolean isAdmin) {
        Account to = new Account();
        to.setEmail(from.getEmail());
        to.setUsername(from.getUsername());
        to.setMobile(from.getMobile());
        if (isAdmin) to.setRole(Role.ROLE_ADMIN);
        else {
            to.setRole(Role.ROLE_USER);
        }
        String token = UUID.randomUUID().toString();
        to.setVerificationToken(token);
        to.setStatus(AccountStatus.INACTIVE);
        to.setPassword(passwordEncoder.encode(from.getPassword()));
        to.setRegisteredAt(LocalDateTime.now());
        return to;
    }

    public AccountRes mapToAccountRes(Account from) {
        AccountRes to = new AccountRes();
        to.setId(from.getId());
        to.setUsername(from.getUsername());
        to.setEmail(from.getEmail());
        to.setMobile(from.getMobile());
        to.setRole(from.getRole().name());
        to.setRegisteredAt(from.getRegisteredAt());
        to.setStatus(from.getStatus().name());
        to.setLastLogin(from.getLastLogin());
        AuthorRes authorRes = authorMapper.mapToAuthorRes(from.getAuthor());
        to.setAuthorRes(authorRes);
        return to;
    }

    public List<AccountRes> mapToListAccountRes(List<Account> from) {
        List<AccountRes> to = new ArrayList<AccountRes>();
        from.forEach(a -> to.add(mapToAccountRes(a)));
        return to;
    }
}
