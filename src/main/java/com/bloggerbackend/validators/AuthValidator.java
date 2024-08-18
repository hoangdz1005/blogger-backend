package com.bloggerbackend.validators;
import com.bloggerbackend.entities.Account;
import com.bloggerbackend.enums.AccountStatus;
import com.bloggerbackend.exceptions.BadRequestException;
import com.bloggerbackend.exceptions.DuplicateEntityException;
import com.bloggerbackend.exceptions.EntityNotFoundException;
import com.bloggerbackend.models.request.AccountReq;
import com.bloggerbackend.models.request.LoginReq;
import com.bloggerbackend.repositories.mysql.AccountRepository;
import com.bloggerbackend.repositories.mysql.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthValidator {
    private static final String ACCOUNT_NOT_EXIST = "Account does not exist!";
    private static final String EMAIL_ALREADY_USED = "Email is already in use!";
    private static final String EMAIL_NOT_EXIST = "Email does not exist!";
    private static final String MOBILE_ALREADY_USED = "Mobile number is already in use!";
    private static final String INVALID_EMAIL_FORMAT = "Invalid email format!";
    private static final String INVALID_MOBILE_FORMAT = "Invalid mobile number format!";
    private static final String ACCOUNT_DISABLED = "Account is disabled!";
    private static final String REFRESH_TOKEN_NOT_EXIST = "Refresh token not found!";

    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public void validateAccountExists(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new EntityNotFoundException(ACCOUNT_NOT_EXIST);
        }
    }

    public void validateEmailExisted(String email) {
        if (accountRepository.existsByEmail(email)) {
            throw new DuplicateEntityException(EMAIL_ALREADY_USED);
        }
    }

    public void validateEmailNotExisted(String email) {
        if (!accountRepository.existsByEmail(email)) {
            throw new DuplicateEntityException(EMAIL_NOT_EXIST);
        }
    }

    public void validateMobileExisted(String mobile) {
        if (accountRepository.existsByMobile(mobile)) {
            throw new DuplicateEntityException(MOBILE_ALREADY_USED);
        }
    }

    public void validateEmailFormat(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!email.matches(emailRegex)) {
            throw new BadRequestException(INVALID_EMAIL_FORMAT);
        }
    }

    public void validateMobileFormat(String mobile) {
        String mobileRegex = "^\\+?[0-9]{10,13}$";
        if (!mobile.matches(mobileRegex)) {
            throw new BadRequestException(INVALID_MOBILE_FORMAT);
        }
    }

    public void validateAccountEnabled(String email) {
        Account account = accountRepository.findByEmail(email).get();
        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new BadRequestException(ACCOUNT_DISABLED);
        }
    }

    public void validateRefreshToken(String token) {
        if(token == null || token.isEmpty() || !refreshTokenRepository.existsByToken(token)) {
            throw new EntityNotFoundException(REFRESH_TOKEN_NOT_EXIST);
        }
    }

    public void validateLogin(LoginReq loginReq) {
        validateEmailNotExisted(loginReq.getEmail());
//        validateAccountEnabled(loginReq.getEmail());
    }

    public void validateRegister(AccountReq accountReq) {
        validateEmailFormat(accountReq.getEmail());
        validateMobileFormat(accountReq.getMobile());
        validateEmailExisted(accountReq.getEmail());
        validateMobileExisted(accountReq.getMobile());
    }
}
