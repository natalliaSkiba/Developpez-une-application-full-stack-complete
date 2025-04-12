package com.openclassrooms.mddapi.service;

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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    @Override
    public Article createArticle(ArticleCreateRequest articleDto) {
        User author = userRepository.findById(articleDto.getAuthorId())
                .orElseThrow(() -> new UserNotFoundException(articleDto.getAuthorId())); // getCurrentAuthenticatedUser();from SecurityContext
        System.out.println("Пользователь найден: " + author.getUsername());
        Topic topic = topicRepository.findById(articleDto.getTopicId())
                .orElseThrow(() -> new TopicNotFoundException(articleDto.getTopicId()));
        System.out.println("Тема найдена: " + topic.getName());
        Article article = Article.builder()
                .title(articleDto.getTitle())
                .content(articleDto.getContent())
                .topic(topic)
                .author(author)
                .build();
        System.out.println("Сохраняем статью: " + article);
        return articleRepository.save(article);
    }

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public Article getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    @Override
    public void deleteArticleById(Long id) {
        if (!articleRepository.existsById(id)) {
            throw new ArticleNotFoundException(id);
        }
        articleRepository.deleteById(id);
    }

    @Override
    public List<Article> getArticlesByTopicId(Long topicId) {
        return articleRepository.findByTopicId(topicId);
    }

    @Override
    public List<Article> getArticlesByUserId(Long userId) {
        return articleRepository.findByAuthorId(userId);
    }

    @Override
    public List<Article> getArticlesSorted(String sortOrder) {
        if ("asc".equalsIgnoreCase(sortOrder)) {
            return articleRepository.findAll(Sort.by(Sort.Direction.ASC, "createdAt"));
        } else {
            return articleRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        }
    }
}

