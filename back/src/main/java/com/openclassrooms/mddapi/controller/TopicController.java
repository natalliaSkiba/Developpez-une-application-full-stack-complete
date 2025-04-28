package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.DTO.TopicResponse;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TopicController {

    private final TopicService topicService;

    @GetMapping
    public ResponseEntity<List<Topic>> getAllTopics() {
        List<Topic> topics = topicService.getAllTopics();
        return ResponseEntity.ok(topics);
    }

    @PostMapping("/subscribe/{topicId}")
    public ResponseEntity<String> subscribeToTopic( @PathVariable Long topicId) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        topicService.subscribe(currentUsername, topicId);
        return ResponseEntity.ok("Subscribed successfully");
    }

    @PostMapping("/unsubscribe/{topicId}")
    public ResponseEntity<String> unsubscribeFromTopic( @PathVariable Long topicId) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        topicService.unsubscribe(currentUsername, topicId);
        return ResponseEntity.ok("Unsubscribed successfully");
    }

    @GetMapping("/subscriptions")
    public ResponseEntity<List<TopicResponse>> getAllTopicsWithStatus() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        List<TopicResponse> topics = topicService.getAllTopicsWithSubscriptionStatus(currentUsername);
        return ResponseEntity.ok(topics);
    }


}
