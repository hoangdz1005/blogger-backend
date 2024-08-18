package com.bloggerbackend.repositories.elastics;

import com.bloggerbackend.entities.Comment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CommentELKRepository extends ElasticsearchRepository<Comment, Long> {
}
