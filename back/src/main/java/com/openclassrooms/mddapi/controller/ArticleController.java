package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.DTO.ArticleCreateRequest;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller that handles all operations related to articles.
 *
 * Provides endpoints to create, retrieve, filter, and optionally delete articles.
 */

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ArticleController {

    private final ArticleService articleService;


    /**
     * Retrieves all articles from the database.
     *
     * @return a list of all articles
     */
    @GetMapping
    public ResponseEntity<List<Article>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }

    /**
     * Retrieves articles based on the given topic ID.
     *
     * @param topicId the ID of the topic
     * @return a list of articles belonging to the specified topic
     */
    @GetMapping("/by-topic/{topicId}")
    public ResponseEntity<List<Article>> getArticlesByTopicId(@PathVariable Long topicId) {
        return ResponseEntity.ok(articleService.getArticlesByTopicId(topicId));
    }

    /**
     * Creates a new article with the authenticated user as the author.
     *
     * @param article the article data to create
     * @return the newly created article
     */
    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody ArticleCreateRequest article) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(articleService.createArticle(article, currentUsername));
    }

    /**
     * Retrieves a specific article by its ID.
     *
     * @param id the ID of the article
     * @return the article with the given ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getArticleById(id));
    }

    /**
     * [Optional] Deletes an article by its ID.
     *
     * This endpoint is not required by the functional specifications
     * and is not used in the frontend. It is available for future use.
     *
     * @param id the ID of the article to delete
     * @return HTTP 204 No Content if deletion is successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticleById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves articles created by the currently authenticated user.
     *
     * @return a list of the user's own articles
     */
    @GetMapping("/feed")
    public ResponseEntity<List<Article>> getArticlesByUser() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(articleService.getArticlesByUser(currentUsername));
    }

    /**
     * Retrieves articles sorted by creation date.
     *
     * @param order the sort direction ("asc" or "desc")
     * @return a list of sorted articles
     */
    @GetMapping("/sorted")
    public ResponseEntity<List<Article>> getSortedArticles(@RequestParam(defaultValue = "desc") String order) {
        return ResponseEntity.ok(articleService.getArticlesSorted(order));
    }
}
