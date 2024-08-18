package com.bloggerbackend.repositories.mysql;
import com.bloggerbackend.entities.Topic;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    boolean existsByName(String name);
    @Query("SELECT t FROM Topic t JOIN t.articles a GROUP BY t ORDER BY COUNT(a) DESC")
    List<Topic> findTopicsWithMostArticles(Pageable pageable);
}
