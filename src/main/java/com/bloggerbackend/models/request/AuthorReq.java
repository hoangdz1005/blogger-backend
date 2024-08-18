package com.bloggerbackend.models.request;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorReq {
    String name;
    String address;
    String avatarImg;
    LocalDateTime dob;
    String profile;
    String socialLink;
}
