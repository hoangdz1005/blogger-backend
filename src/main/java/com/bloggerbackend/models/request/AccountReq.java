package com.bloggerbackend.models.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountReq {
    String username;
    String password;
    String email;
    String mobile;
}
