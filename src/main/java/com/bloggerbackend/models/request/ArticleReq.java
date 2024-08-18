package com.bloggerbackend.models.request;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ArticleReq {
    String title;
    String summary;
    int minRead;
    String content;
    Long topicId;
}