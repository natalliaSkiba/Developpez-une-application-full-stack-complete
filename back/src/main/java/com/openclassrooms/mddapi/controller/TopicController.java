package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.DTO.TopicResponse;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for managing programming topics and user subscriptions.
 *
 * Provides endpoints to list topics, subscribe/unsubscribe, and view subscription status.
 */
@RestController
@RequestMapping("/topics")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TopicController {

    private final TopicService topicService;

    /**
     * Controller responsible for managing programming topics and user subscriptions.
     *
     * Provides endpoints to list topics, subscribe/unsubscribe, and view subscription status.
     */
    @GetMapping
    public ResponseEntity<List<Topic>> getAllTopics() {
        List<Topic> topics = topicService.getAllTopics();
        return ResponseEntity.ok(topics);
    }

    /**
     * Subscribes the currently authenticated user to a topic.
     *
     * @param topicId the ID of the topic to subscribe to
     * @return confirmation message
     */
    @PostMapping("/subscribe/{topicId}")
    public ResponseEntity<String> subscribeToTopic(@PathVariable Long topicId) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        topicService.subscribe(currentUsername, topicId);
        return ResponseEntity.ok("Subscribed successfully");
    }

    /**
     * Unsubscribes the currently authenticated user from a topic.
     *
     * @param topicId the ID of the topic to unsubscribe from
     * @return confirmation message
     */
    @PostMapping("/unsubscribe/{topicId}")
    public ResponseEntity<String> unsubscribeFromTopic(@PathVariable Long topicId) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        topicService.unsubscribe(currentUsername, topicId);
        return ResponseEntity.ok("Unsubscribed successfully");
    }

    /**
     * Retrieves all topics with the subscription status for the current user.
     *
     * @return list of topics with a 'subscribed' flag
     */
    @GetMapping("/subscriptions")
    public ResponseEntity<List<TopicResponse>> getAllTopicsWithStatus() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        List<TopicResponse> topics = topicService.getAllTopicsWithSubscriptionStatus(currentUsername);
        return ResponseEntity.ok(topics);
    }
}
