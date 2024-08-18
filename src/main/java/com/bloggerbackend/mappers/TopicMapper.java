package com.bloggerbackend.mappers;

import com.bloggerbackend.entities.Topic;
import com.bloggerbackend.models.request.TopicReq;
import com.bloggerbackend.models.response.TopicRes;
import com.bloggerbackend.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicMapper {
    public Topic mapToTopic(TopicReq from) {
        Topic to = new Topic();
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setImageBase64(from.getImageBase64());
        to.setSlug(StringUtils.mapToSlug(from.getName()));
        return to;
    }
    public void updateTopic (Topic to, TopicReq from) {
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setSlug(StringUtils.mapToSlug(from.getName()));
        to.setImageBase64(from.getImageBase64());
    }
    public TopicRes mapToTopicRes(Topic from) {
        TopicRes to = new TopicRes();
        to.setId(from.getId());
        to.setDescription(from.getDescription());
        to.setSlug(from.getSlug());
        to.setImageBase64(from.getImageBase64());
        to.setName(from.getName());
        return to;
    }
    public List<TopicRes> mapToListTopicRes(List<Topic> from) {
        List<TopicRes> to = new ArrayList<>();
        from.forEach(topic -> to.add(mapToTopicRes(topic)));
        return to;
    }
}
