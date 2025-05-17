package com.openclassrooms.mddapi.repositiry;

import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByArticle(Article article);
}
