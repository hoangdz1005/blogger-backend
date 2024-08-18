package com.bloggerbackend.apis;

import com.bloggerbackend.constants.AppConstants;
import com.bloggerbackend.models.request.AccountReq;
import com.bloggerbackend.models.response.AccountRes;
import com.bloggerbackend.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppConstants.BASE_API_URL)
public class AccountResources {
    private final AuthService authService;

    public AccountResources(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/accounts")
    public ResponseEntity<AccountRes> createAccount(@RequestBody AccountReq accountReq) {
        return new ResponseEntity<>(authService.register(accountReq), HttpStatus.OK);
    }
}

