package com.bloggerbackend.exceptions;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import java.io.IOException;


public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        final String AUTHENTICATION_EXCEPTION = "Authentication Exception";
        final String AUTHENTICATION_EXCEPTION_DESCRIPTION = "Authentication failed! Please check your access token";
        String jsonResponse = String.format("{\"code\": \"%d\", \"description\": \"%s\", \"reasonCode\": \"%s\"}",
                HttpStatus.UNAUTHORIZED.value(), AUTHENTICATION_EXCEPTION_DESCRIPTION, AUTHENTICATION_EXCEPTION);

        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(jsonResponse);
    }
}