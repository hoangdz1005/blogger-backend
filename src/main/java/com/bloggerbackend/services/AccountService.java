package com.bloggerbackend.services;
import com.bloggerbackend.entities.Account;
import com.bloggerbackend.entities.Author;
import com.bloggerbackend.exceptions.EntityNotFoundException;
import com.bloggerbackend.mappers.AccountMapper;
import com.bloggerbackend.mappers.AuthorMapper;
import com.bloggerbackend.models.request.AccountReq;
import com.bloggerbackend.models.response.AccountRes;
import com.bloggerbackend.repositories.mysql.AccountRepository;
import com.bloggerbackend.repositories.mysql.AuthorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public List<AccountRes> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accountMapper.mapToListAccountRes(accounts);
    }

    public AccountRes getAccountById(long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Account not found"));
        return accountMapper.mapToAccountRes(account);
    }
}
