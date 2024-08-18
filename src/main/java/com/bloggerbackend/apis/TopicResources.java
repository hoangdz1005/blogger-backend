package com.bloggerbackend.apis;

import com.bloggerbackend.constants.AppConstants;
import com.bloggerbackend.models.request.TopicReq;
import com.bloggerbackend.models.response.TopicRes;
import com.bloggerbackend.services.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(AppConstants.BASE_API_URL)
public class TopicResources {
    private final TopicService topicService;

    public TopicResources(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/topics")
    public ResponseEntity<List<TopicRes>> getTopics(@RequestParam(required = false) Integer pageStart,
                                                    @RequestParam(required = false) Integer pageSize) {
        return new ResponseEntity<>(topicService.getTopics(pageStart, pageSize), HttpStatus.OK);
    }

    @PostMapping("/topics")
    public ResponseEntity<TopicRes> createTopic(@RequestBody TopicReq topicReq) {
        return new ResponseEntity<>(topicService.createTopic(topicReq), HttpStatus.OK);
    }

    @GetMapping("/topics/{id}")
    public ResponseEntity<TopicRes> getTopic(@PathVariable Long id) {
        return new ResponseEntity<>(topicService.getTopic(id), HttpStatus.OK);
    }

    @PutMapping("/topics/{id}")
    public ResponseEntity<TopicRes> updateTopic(@PathVariable Long id, @RequestBody TopicReq topicReq) {
        return new ResponseEntity<>(topicService.updateTopic(id, topicReq), HttpStatus.OK);
    }

    @DeleteMapping("/topics/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/topics/popular")
    public ResponseEntity<List<TopicRes>> getPopularTopics(@RequestParam(required = false) Integer limit) {
        return new ResponseEntity<>(topicService.getTopTopicsWithMostArticles(limit), HttpStatus.OK);
    }
}
