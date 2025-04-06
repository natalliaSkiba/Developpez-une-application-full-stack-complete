package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByArticleId(Long articleId);

    Comment addComment(Long articleId, Long userId, String content);
}
