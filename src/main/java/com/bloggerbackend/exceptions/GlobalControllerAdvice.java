package com.bloggerbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    private static final String REASON_NOT_FOUND = "Entity Not Found";
    private static final String BAD_REQUEST_EXCEPTION = "Bad Request Exception";
    private static final String REASON_INTERNAL_ERROR = "Internal Error";
    private static final String DUPLICATE_ENTITY_EXCEPTION = "Duplicate Entity Exception";
    private static final String TOO_MANY_REQUESTS = "Too Many Requests";
    private static final String ACCESS_DENIED_EXCEPTION = "Access Denied Exception";
    private static final String ACCESS_DENIED_EXCEPTION_DESCRIPTION = "No permissions to access resources!";
    private static final String AUTHENTICATION_EXCEPTION = "Authentication Exception";
    private static final String AUTHENTICATION_EXCEPTION_DESCRIPTION = "Authentication failed! Access token is invalid or expired";

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Error> handleEntityNotFoundException(EntityNotFoundException exception) {
        Error error = new Error();
        error.setCode(Integer.toString(HttpStatus.NOT_FOUND.value()));
        error.setDescription(exception.getMessage());
        error.setReasonCode(REASON_NOT_FOUND);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<Error> handleDuplicateEntityException(DuplicateEntityException exception) {
        Error error = new Error();
        error.setCode(Integer.toString(HttpStatus.CONFLICT.value()));
        error.setDescription(exception.getMessage());
        error.setReasonCode(DUPLICATE_ENTITY_EXCEPTION);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Error> handleBadRequestException(BadRequestException exception) {
        Error error = new Error();
        error.setCode(Integer.toString(HttpStatus.BAD_REQUEST.value()));
        error.setDescription(exception.getMessage());
        error.setReasonCode(BAD_REQUEST_EXCEPTION);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<Error> handleAccessDeniedException() {
        Error error = new Error();
        error.setCode(Integer.toString(HttpStatus.FORBIDDEN.value()));
        error.setDescription(ACCESS_DENIED_EXCEPTION_DESCRIPTION);
        error.setReasonCode(ACCESS_DENIED_EXCEPTION);
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Error> handleAuthenticationException() {
        Error error = new Error();
        error.setCode(Integer.toString(HttpStatus.UNAUTHORIZED.value()));
        error.setDescription(AUTHENTICATION_EXCEPTION_DESCRIPTION);
        error.setReasonCode(AUTHENTICATION_EXCEPTION);
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(TooManyRequestException.class)
    public ResponseEntity<Error> handleTooManyRequestsException(Exception exception) {
        Error error = new Error();
        error.setCode(Integer.toString(HttpStatus.TOO_MANY_REQUESTS.value()));
        error.setDescription(exception.getMessage());
        error.setReasonCode(TOO_MANY_REQUESTS);
        return new ResponseEntity<>(error, HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleInternalErrorException(Exception exception) {
        Error error = new Error();
        error.setCode(Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        error.setDescription(exception.getMessage());
        error.setReasonCode(REASON_INTERNAL_ERROR);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
