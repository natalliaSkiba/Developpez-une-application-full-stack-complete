package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller that handles comment-related operations on articles.
 *
 * Provides endpoints to retrieve and add comments for a specific article.
 */
@RestController
@RequestMapping("/articles/{articleId}/comments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {

    private final CommentService commentService;

    /**
     * Retrieves all comments for a given article.
     *
     * @param articleId the ID of the article
     * @return a list of comments related to the article
     */
    @GetMapping
    public ResponseEntity<List<Comment>> getCommentsByArticle(@PathVariable Long articleId) {
        List<Comment> comments = commentService.getCommentsByArticleId(articleId);

        return ResponseEntity.ok(comments);
    }

    /**
     * Adds a comment to the specified article.
     *
     * @param articleId the ID of the article to comment on
     * @param comment the comment content
     * @return the created comment object
     */
    @PostMapping
    public ResponseEntity<Comment> addComment(
            @PathVariable Long articleId,
            @RequestParam String comment
    ) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Comment createdComment = commentService.addComment(articleId, currentUsername, comment);

        return ResponseEntity.ok(createdComment);
    }
}
