package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/subscribe/{userId}/{topicId}")
    public ResponseEntity<String> subscribeToTopic(@PathVariable Long userId, @PathVariable Long topicId) {
        topicService.subscribe(userId, topicId);
        return ResponseEntity.ok("Subscribed successfully");
    }

    @PostMapping("/unsubscribe/{userId}/{topicId}")
    public ResponseEntity<String> unsubscribeFromTopic(@PathVariable Long userId, @PathVariable Long topicId) {
        topicService.unsubscribe(userId, topicId);
        return ResponseEntity.ok("Unsubscribed successfully");
    }
}
