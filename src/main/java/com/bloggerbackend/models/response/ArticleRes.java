package com.bloggerbackend.models.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(indexName = "articles")
public class ArticleRes {
    Long id;
    String title;
    String slug;
    String summary;
    int minRead;
    String imageBase64;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocalDateTime publishedAt;
    String content;
    TopicRes topic;
    AuthorRes author;
    boolean isSaved;
    int numberOfComments;
}
