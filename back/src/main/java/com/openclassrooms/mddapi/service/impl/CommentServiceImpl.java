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

/**
 * Service implementation for managing comments on articles.
 *
 * Provides methods to retrieve comments for a specific article and to add new comments.
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;


    /**
     * Retrieves all comments related to a specific article.
     *
     * @param articleId the ID of the article
     * @return a list of comments associated with the article
     * @throws ArticleNotFoundException if the article does not exist
     */
    @Override
    public List<Comment> getCommentsByArticleId(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        return commentRepository.findByArticle(article);
    }

    /**
     * Adds a new comment to the specified article, posted by the given user.
     *
     * @param articleId the ID of the article
     * @param username the username of the comment author
     * @param content the comment text
     * @return the created Comment entity
     * @throws ArticleNotFoundException if the article does not exist
     * @throws UserNotFoundException if the user does not exist
     */
    @Override
    public Comment addComment(Long articleId, String username, String content) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        Comment comment = Comment.builder()
                .content(content)
                .article(article)
                .author(user)
                .build();

        return commentRepository.save(comment);
    }
}
