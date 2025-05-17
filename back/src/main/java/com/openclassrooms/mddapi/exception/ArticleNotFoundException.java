package com.openclassrooms.mddapi.exception;

public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException(Long articleId) {
        super("Article non trouvé avec l'id : " + articleId);
    }
}
