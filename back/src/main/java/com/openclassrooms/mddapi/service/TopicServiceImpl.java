package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.exception.TopicNotFoundException;
import com.openclassrooms.mddapi.exception.UserNotFoundException;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repositiry.TopicRepository;
import com.openclassrooms.mddapi.repositiry.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
