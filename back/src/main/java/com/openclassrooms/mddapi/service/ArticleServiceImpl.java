package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.exception.ArticleNotFoundException;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.repositiry.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Override
    public Article createArticle(Article article) {
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
        return articleRepository.findByUserId(userId);
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

