package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.DTO.TopicResponse;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Topics", description = "Endpoints for viewing and managing topic subscriptions")
public class TopicController {

    private final TopicService topicService;

    /**
     * Controller responsible for managing programming topics and user subscriptions.
     *
     * Provides endpoints to list topics, subscribe/unsubscribe, and view subscription status.
     */
    @Operation(
            summary = "Get all available topics",
            description = "Returns the list of all topics",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
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
    @Operation(
            summary = "Subscribe to a topic",
            description = "Subscribe the current user to a topic by ID",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
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
    @Operation(
            summary = "Unsubscribe from a topic",
            description = "Remove a topic subscription by ID for the current user",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
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
    @Operation(
            summary = "Get all topics with subscription status",
            description = "Returns all topics and indicates whether the current user is subscribed to each",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @GetMapping("/subscriptions")
    public ResponseEntity<List<TopicResponse>> getAllTopicsWithStatus() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        List<TopicResponse> topics = topicService.getAllTopicsWithSubscriptionStatus(currentUsername);
        return ResponseEntity.ok(topics);
    }
}
