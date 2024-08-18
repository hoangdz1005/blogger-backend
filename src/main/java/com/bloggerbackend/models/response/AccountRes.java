package com.bloggerbackend.models.response;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(indexName = "accounts")
public class AccountRes {
    Long id;
    String username;
    String email;
    String mobile;
    String status;
    String role;
    LocalDateTime registeredAt;
    LocalDateTime lastLogin;
    AuthorRes authorRes;
}
