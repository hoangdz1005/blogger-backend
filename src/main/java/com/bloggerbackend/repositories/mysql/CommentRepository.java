package com.bloggerbackend.repositories.mysql;

import com.bloggerbackend.entities.Article;
import com.bloggerbackend.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.article.id = :articleId")
    List<Comment> findByArticleId(@Param("articleId") Long articleId);
}
