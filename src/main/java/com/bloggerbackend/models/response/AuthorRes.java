package com.bloggerbackend.models.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(indexName = "authors")
public class AuthorRes {
    Long id;
    String name;
    String address;
    String email;
    String status;
    String avatarImg;
    LocalDateTime joinedDate;
    LocalDateTime dob;
    String profile;
    String socialLink;
}
