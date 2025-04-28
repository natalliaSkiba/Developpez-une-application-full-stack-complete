package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.DTO.ArticleCreateRequest;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public ResponseEntity<List<Article>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }

    @GetMapping("/topics/{topicId}/articles")
    public ResponseEntity<List<Article>> getArticlesByTopicId(@PathVariable Long topicId) {
        return ResponseEntity.ok(articleService.getArticlesByTopicId(topicId));
    }

    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody ArticleCreateRequest article) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(articleService.createArticle(article,currentUsername));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getArticleById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticleById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/feed")
    public ResponseEntity<List<Article>> getArticlesByUser() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(articleService.getArticlesByUser(currentUsername));
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Article>> getSortedArticles(@RequestParam(defaultValue = "desc") String order) {
        return ResponseEntity.ok(articleService.getArticlesSorted(order));
    }

}
