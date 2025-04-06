package com.openclassrooms.mddapi.repositiry;

import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
    List<Article> findByTopicId(Long topicId);

    List<Article> findByUserId(Long userId);

  }
