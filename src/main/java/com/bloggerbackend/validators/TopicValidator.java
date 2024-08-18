package com.bloggerbackend.validators;

import com.bloggerbackend.exceptions.DuplicateEntityException;
import com.bloggerbackend.exceptions.EntityNotFoundException;
import com.bloggerbackend.models.request.TopicReq;
import com.bloggerbackend.repositories.mysql.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TopicValidator {
    private static final String TOPIC_NOT_EXIST = "Topic does not exist!";
    private static final String TOPIC_NAME_ALREADY_EXIST = "Topic name is already in use!";

    private final TopicRepository topicRepository;
    public void validateTopicExists(Long topicId) {
        if (!topicRepository.existsById(topicId)) {
            throw new EntityNotFoundException(TOPIC_NOT_EXIST);
        }
    }
    public void validateTopicNameExisted(String name) {
        if (topicRepository.existsByName(name)) {
            throw new DuplicateEntityException(TOPIC_NAME_ALREADY_EXIST);
        }
    }
    public void validateUpdateTopic(Long topicId, TopicReq topicReq) {
        validateTopicExists(topicId);
        validateTopicNameExisted(topicReq.getName());
    }
}
