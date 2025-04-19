package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.DTO.TopicResponse;
import com.openclassrooms.mddapi.exception.TopicNotFoundException;
import com.openclassrooms.mddapi.exception.UserNotFoundException;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repositiry.TopicRepository;
import com.openclassrooms.mddapi.repositiry.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService{

    private final TopicRepository topicRepository;

    private final UserRepository userRepository;

    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @Override
    public void subscribe(Long userId, Long topicId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new TopicNotFoundException(topicId));
        user.getSubscriptions().add(topic);
        userRepository.save(user);
    }

    @Override
    public void unsubscribe(Long userId, Long topicId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new TopicNotFoundException(topicId));
        user.getSubscriptions().remove(topic);
        userRepository.save(user);
    }

    @Override
    public List<TopicResponse> getAllTopicsWithSubscriptionStatus(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));

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
