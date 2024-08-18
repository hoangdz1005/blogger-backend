package com.bloggerbackend.repositories.elastics;

import com.bloggerbackend.entities.Topic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

public interface TopicELKRepository extends ElasticsearchRepository<Topic, Long> {
}
