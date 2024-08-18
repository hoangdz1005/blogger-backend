package com.bloggerbackend.repositories.elastics;

import com.bloggerbackend.entities.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleELKRepository extends ElasticsearchRepository<Article, Long> {
}
