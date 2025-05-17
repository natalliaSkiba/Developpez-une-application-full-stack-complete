package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.DTO.TopicResponse;
import com.openclassrooms.mddapi.exception.TopicNotFoundException;
import com.openclassrooms.mddapi.exception.UserNotFoundException;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repositiry.TopicRepository;
import com.openclassrooms.mddapi.repositiry.UserRepository;
import com.openclassrooms.mddapi.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing programming topics and user subscriptions.
 * <p>
 * Provides methods to retrieve all topics, subscribe or unsubscribe from topics,
 * and retrieve topic list with subscription status for the current user.
 */
@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    private final UserRepository userRepository;

    /**
     * Retrieves all available topics.
     *
     * @return list of all topics
     */
    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    /**
     * Subscribes the specified user to a topic.
     *
     * @param username the username of the user
     * @param topicId  the ID of the topic to subscribe to
     * @throws UserNotFoundException  if the user does not exist
     * @throws TopicNotFoundException if the topic does not exist
     */
    @Override
    public void subscribe(String username, Long topicId) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new TopicNotFoundException(topicId));
        user.getSubscriptions().add(topic);
        userRepository.save(user);
    }

    /**
     * Unsubscribes the specified user from a topic.
     *
     * @param username the username of the user
     * @param topicId  the ID of the topic to unsubscribe from
     * @throws UserNotFoundException  if the user does not exist
     * @throws TopicNotFoundException if the topic does not exist
     */
    @Override
    public void unsubscribe(String username, Long topicId) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new TopicNotFoundException(topicId));
        user.getSubscriptions().remove(topic);
        userRepository.save(user);
    }

    /**
     * Retrieves all topics along with subscription status for the given user.
     *
     * @param username the username of the user
     * @return a list of TopicResponse objects containing topic info and subscription status
     * @throws UserNotFoundException if the user does not exist
     */
    @Override
    public List<TopicResponse> getAllTopicsWithSubscriptionStatus(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        List<Topic> allTopics = topicRepository.findAll();
        List<Topic> subscribedTopics = user.getSubscriptions();

        return allTopics.stream()
                .map(topic -> new TopicResponse(
                        topic.getId(),
                        topic.getName(),
                        topic.getDescription(),
                        subscribedTopics.contains(topic)

                ))
                .collect(Collectors.toList());
    }
}
