package com.bloggerbackend.entities;

import com.bloggerbackend.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(indexName = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "address")
    String address;

    @Column(name = "email", unique = true)
    String email;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    AccountStatus status;

    @Column(name = "avatar_img")
    String avatarImg;

    @Column(name = "joined_date")
    LocalDateTime joinedDate;

    @Column(name = "dob")
    LocalDateTime dob;

    @Column(name = "profile", columnDefinition = "TEXT")
    String profile;

    @Column(name = "social_link")
    String socialLink;

    @ManyToMany
    @JoinTable(
            name = "author_saved_articles",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    private List<Article> savedArticles;

    @ManyToMany
    @JoinTable(
            name = "author_followers",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private List<Author> following = new ArrayList<>();

    @ManyToMany(mappedBy = "following")
    private List<Author> followers = new ArrayList<>();

}

