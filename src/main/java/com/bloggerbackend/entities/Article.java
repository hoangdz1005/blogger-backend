package com.bloggerbackend.entities;

import com.bloggerbackend.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "articles")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(indexName = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "title", length = 75, nullable = false)
    String title;

    @Column(name = "slug", length = 100, unique = true, nullable = false)
    String slug;

    @Column(name = "summary", columnDefinition = "TEXT")
    String summary;

    @Column(name = "min_read")
    int minRead;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    ArticleStatus status;

    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "published_at")
    LocalDateTime publishedAt;

    @Column(name = "content", columnDefinition = "LONGTEXT")
    String content;

    @Lob
    @Column(name = "image_base64", columnDefinition = "LONGTEXT")
    String imageBase64;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    Topic topic;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToMany(mappedBy = "savedArticles")
    private List<Author> likedByAuthors;
}

