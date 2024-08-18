package com.bloggerbackend.models.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenRes {
    private String refreshToken;
    private String accessToken;
}
