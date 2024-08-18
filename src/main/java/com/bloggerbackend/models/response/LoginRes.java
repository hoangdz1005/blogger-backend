package com.bloggerbackend.models.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRes {
    private String accessToken;
    private String refreshToken;
}
