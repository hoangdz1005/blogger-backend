package com.bloggerbackend.entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(indexName = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "content", length = 100, nullable = false)
    String content;

    @Column(name = "published_at")
    LocalDateTime publishedAt;

    @ManyToOne
    @JoinColumn(name = "article_id")
    Article article;

    @ManyToOne
    @JoinColumn(name = "author_id")
    Author author;
}
