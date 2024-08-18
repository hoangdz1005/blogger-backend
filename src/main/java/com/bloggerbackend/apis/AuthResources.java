package com.bloggerbackend.apis;

import com.bloggerbackend.constants.AppConstants;
import com.bloggerbackend.models.request.AccountReq;
import com.bloggerbackend.models.request.LoginReq;
import com.bloggerbackend.models.request.RefreshTokenReq;
import com.bloggerbackend.models.response.AccountRes;
import com.bloggerbackend.models.response.LoginRes;
import com.bloggerbackend.models.response.RefreshTokenRes;
import com.bloggerbackend.services.AuthService;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstants.BASE_API_URL)
public class AuthResources {
    private final AuthService authService;

    public AuthResources(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<AccountRes> register(@RequestBody AccountReq request) {
        AccountRes accountRes = authService.register(request);
        return new ResponseEntity<>(accountRes, HttpStatus.OK);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginRes> login(@RequestBody LoginReq request) {
        LoginRes loginRes = authService.login(request);
        return new ResponseEntity<>(loginRes, HttpStatus.OK);
    }

    @PostMapping("/auth/refresh-token")
    public ResponseEntity<RefreshTokenRes> refreshToken(HttpServletRequest request) {
        RefreshTokenRes refreshTokenRes = authService.refreshToken(request);
        return new ResponseEntity<>(refreshTokenRes, HttpStatus.OK);
    }
    @GetMapping("/auth/verify-email")
    public ResponseEntity<String> verifyAccount(@RequestParam String token) {
        return new ResponseEntity<>(authService.validateAccount(token), HttpStatus.OK);
    }
}
