package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles/{articleId}/comments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<Comment>> getCommentsByArticle(@PathVariable Long articleId) {
        List<Comment> comments = commentService.getCommentsByArticleId(articleId);

        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(
            @PathVariable Long articleId,
            @RequestParam Long userId,
            @RequestParam String comment
    ){
        Comment createComment = commentService.addComment(articleId,userId,comment);

        return ResponseEntity.ok(createComment);
    }
}
