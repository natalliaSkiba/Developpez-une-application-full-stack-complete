package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.DTO.TopicResponse;
import com.openclassrooms.mddapi.model.Topic;

import java.util.List;

public interface TopicService {
    List<Topic> getAllTopics();

    void subscribe(String username, Long topicId);

    void unsubscribe(String username, Long topicId);

    List<TopicResponse> getAllTopicsWithSubscriptionStatus(String username);

}
