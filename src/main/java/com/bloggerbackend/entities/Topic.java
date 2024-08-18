package com.bloggerbackend.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "topics")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(indexName = "topics")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", length = 75, nullable = false)
    String name;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @Column(name = "slug", length = 100, nullable = false)
    String slug;

    @Column(name = "image_base64")
    String imageBase64;

    @OneToMany(mappedBy = "topic")
    private List<Article> articles = new ArrayList<>();
}
