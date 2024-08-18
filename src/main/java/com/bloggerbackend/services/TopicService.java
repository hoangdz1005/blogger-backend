package com.bloggerbackend.services;

import com.bloggerbackend.constants.AppConfigs;
import com.bloggerbackend.entities.Topic;
import com.bloggerbackend.exceptions.EntityNotFoundException;
import com.bloggerbackend.mappers.TopicMapper;
import com.bloggerbackend.models.request.TopicReq;
import com.bloggerbackend.models.response.TopicRes;
import com.bloggerbackend.repositories.elastics.TopicELKRepository;
import com.bloggerbackend.repositories.mysql.TopicRepository;
import com.bloggerbackend.validators.TopicValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class TopicService {
    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;
    private final TopicValidator topicValidator;

    public TopicService(TopicRepository topicRepository, TopicMapper topicMapper, TopicValidator topicValidator) {
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
        this.topicValidator = topicValidator;
    }

//    public List<TopicRes> getTopics(Integer page, Integer pageSize, String search, String sort) {
//        NativeQueryBuilder nativeQueryBuilder = NativeQuery.builder();
//        SearchUtils.addPageRequest(nativeQueryBuilder, page, pageSize);
//        SearchUtils.addQuery(nativeQueryBuilder, search, SearchConst.TOPIC_FIELDS_MATCH);
//        if(sort!=null && !sort.isEmpty()) {
//            String field = SearchUtils.getFiledSort(sort);
//            String fieldSort = field.equals(SearchConst.TOPIC_FIELD_NAME) ?  SearchConst.TOPIC_SORT_NAME : SearchConst.TOPIC_SORT_DESCRIPTION;
//            String valueSort = SearchUtils.getValueSort(sort);
//            var sortOption = valueSort.equals(SearchConst.SORT_ASC) ? Sort.by(Sort.Order.asc(fieldSort)) : Sort.by(Sort.Order.desc(fieldSort));
//            nativeQueryBuilder.withSort(sortOption);
//        }
//        List<Topic> topics = elasticsearchTemplate.search(nativeQueryBuilder.build(), Topic.class).stream().map(SearchHit::getContent).toList();
//            return topicMapper.mapToListTopicRes(topics);
//    }

    public List<TopicRes> getTopics(Integer pageStart, Integer pageSize) {
        Pageable pageable = PageRequest.of(
                pageStart != null ? pageStart : AppConfigs.PAGE_START,
                pageSize != null ? pageSize : AppConfigs.PAGE_SIZE_DEFAULT
        );
        List<Topic> topics = topicRepository.findAll(pageable).getContent();
        return topicMapper.mapToListTopicRes(topics);
    }

    public TopicRes createTopic(TopicReq topicReq) {
        topicValidator.validateTopicNameExisted(topicReq.getName());
        Topic topic = topicMapper.mapToTopic(topicReq);
        topicRepository.save(topic);
        return topicMapper.mapToTopicRes(topic);
    }

    public TopicRes getTopic(Long id) {
        topicValidator.validateTopicExists(id);
        Topic topic = topicRepository.findById(id).get();
        return topicMapper.mapToTopicRes(topic);
    }

    public TopicRes updateTopic(Long id, TopicReq topicReq) {
        topicValidator.validateUpdateTopic(id,topicReq);
        Topic topic = topicRepository.findById(id).get();
        topicMapper.updateTopic(topic, topicReq);
        return topicMapper.mapToTopicRes(topicRepository.save(topic));
    }

    public void deleteTopic(Long id) {
        topicValidator.validateTopicExists(id);
        topicRepository.deleteById(id);
    }

    public List<TopicRes> getTopTopicsWithMostArticles(Integer limit) {
        Pageable pageable = PageRequest.of(
                0,
                limit != null ? limit : AppConfigs.LIMIT_DEFAULT
        );
        List<Topic> topics = topicRepository.findTopicsWithMostArticles(pageable);
        return topicMapper.mapToListTopicRes(topics);
    }
}
