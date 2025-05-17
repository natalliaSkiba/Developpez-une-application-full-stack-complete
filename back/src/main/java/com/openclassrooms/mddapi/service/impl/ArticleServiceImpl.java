package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.DTO.ArticleCreateRequest;
import com.openclassrooms.mddapi.exception.ArticleNotFoundException;
import com.openclassrooms.mddapi.exception.TopicNotFoundException;
import com.openclassrooms.mddapi.exception.UserNotFoundException;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repositiry.ArticleRepository;
import com.openclassrooms.mddapi.repositiry.TopicRepository;
import com.openclassrooms.mddapi.repositiry.UserRepository;
import com.openclassrooms.mddapi.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing articles.
 * <p>
 * Handles creation, retrieval, deletion, sorting, and filtering of articles
 * based on topic or user subscriptions.
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    /**
     * Creates a new article authored by the given user and assigned to a topic.
     *
     * @param articleDto the article creation data (title, content, topicId)
     * @param username   the username of the article's author
     * @return the created article
     * @throws UserNotFoundException  if the user does not exist
     * @throws TopicNotFoundException if the topic does not exist
     */
    @Override
    public Article createArticle(ArticleCreateRequest articleDto, String username) {
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        Topic topic = topicRepository.findById(articleDto.getTopicId())
                .orElseThrow(() -> new TopicNotFoundException(articleDto.getTopicId()));

        Article article = Article.builder()
                .title(articleDto.getTitle())
                .content(articleDto.getContent())
                .topic(topic)
                .author(author)
                .build();

        return articleRepository.save(article);
    }

    /**
     * Retrieves all articles.
     *
     * @return a list of all articles
     */
    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    /**
     * Retrieves an article by its ID.
     *
     * @param id the ID of the article
     * @return the article with the given ID
     * @throws ArticleNotFoundException if the article does not exist
     */
    @Override
    public Article getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    /**
     * Deletes an article by its ID.
     *
     * @param id the ID of the article to delete
     * @throws ArticleNotFoundException if the article does not exist
     */
    @Override
    public void deleteArticleById(Long id) {
        if (!articleRepository.existsById(id)) {
            throw new ArticleNotFoundException(id);
        }
        articleRepository.deleteById(id);
    }

    /**
     * Retrieves all articles related to a specific topic.
     *
     * @param topicId the topic ID
     * @return a list of articles for the given topic
     */
    @Override
    public List<Article> getArticlesByTopicId(Long topicId) {
        return articleRepository.findByTopicId(topicId);
    }

    /**
     * Retrieves all articles sorted by creation date.
     *
     * @param sortOrder the sort direction ("asc" or "desc")
     * @return a list of articles sorted accordingly
     */
    @Override
    public List<Article> getArticlesSorted(String sortOrder) {
        if ("asc".equalsIgnoreCase(sortOrder)) {
            return articleRepository.findAll(Sort.by(Sort.Direction.ASC, "createdAt"));
        } else {
            return articleRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        }
    }

    /**
     * Retrieves articles that match the current user's topic subscriptions,
     * ordered by creation date descending.
     *
     * @param username the username of the logged-in user
     * @return a list of articles from subscribed topics
     * @throws UserNotFoundException if the user does not exist
     */
    @Override
    public List<Article> getArticlesByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        List<Topic> subscriptions = user.getSubscriptions();

        if (subscriptions.isEmpty()) {
            return List.of();
        }

        return articleRepository.findByTopicInOrderByCreatedAtDesc(subscriptions);
    }
}

