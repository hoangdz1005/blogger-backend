package com.bloggerbackend.repositories.mysql;

import com.bloggerbackend.entities.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a JOIN a.likedByAuthors la WHERE la.id = :authorId")
    List<Article> findArticlesLikedByAuthor(@Param("authorId") Long authorId);

    @Query("SELECT a FROM Article a WHERE a.author.id = :authorId")
    List<Article> findArticlesByAuthorId(@Param("authorId") Long authorId);

    @Query("SELECT a FROM Article a WHERE a.topic.id = :topicId")
    List<Article> findArticlesByTopicId(@Param("topicId") Long topicId);

    Page<Article> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
}
