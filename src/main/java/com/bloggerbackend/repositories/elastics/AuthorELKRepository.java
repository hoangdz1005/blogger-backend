package com.bloggerbackend.repositories.elastics;

import com.bloggerbackend.entities.Author;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AuthorELKRepository extends ElasticsearchRepository<Author, Long> {
}
