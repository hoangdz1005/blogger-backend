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
@Document(indexName = "comments")
public class CommentRes {
    Long id;
    String content;
    LocalDateTime publishedAt;
    Long articleId;
    Long authorId;
    String authorName;
    String authorAvatar;
}
