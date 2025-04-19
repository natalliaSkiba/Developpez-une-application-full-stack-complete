package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.DTO.TopicResponse;
import com.openclassrooms.mddapi.model.Topic;

import java.util.List;

public interface TopicService {
    List<Topic> getAllTopics();

    void subscribe(Long userId, Long topicId);

    void unsubscribe(Long userId, Long topicId);

    List<TopicResponse> getAllTopicsWithSubscriptionStatus(Long userId);

}
