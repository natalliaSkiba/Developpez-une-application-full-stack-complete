package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.exception.ArticleNotFoundException;
import com.openclassrooms.mddapi.exception.UserNotFoundException;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repositiry.ArticleRepository;
import com.openclassrooms.mddapi.repositiry.CommentRepository;
import com.openclassrooms.mddapi.repositiry.UserRepository;
import com.openclassrooms.mddapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Override
    public List<Comment> getCommentsByArticleId(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        return commentRepository.findByArticle(article);
    }

    @Override
    public Comment addComment(Long articleId, Long userId, String content) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Comment comment = Comment.builder()
                .content(content)
                .article(article)
                .author(user)
                .build();

        return commentRepository.save(comment);
    }
}
