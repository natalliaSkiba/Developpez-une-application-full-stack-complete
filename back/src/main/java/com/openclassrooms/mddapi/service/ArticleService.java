package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.DTO.ArticleCreateRequest;
import com.openclassrooms.mddapi.model.Article;

import java.util.List;

public interface ArticleService {

    Article createArticle(ArticleCreateRequest article);

    List<Article> getAllArticles();

    Article getArticleById(Long id);

    void deleteArticleById(Long id);

    List<Article> getArticlesByTopicId(Long topicId);

    List<Article> getArticlesByUserId(Long userId);

    List<Article> getArticlesSorted(String sortOrder);

}
