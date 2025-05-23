package com.openclassrooms.mddapi.DTO;

import lombok.Data;

@Data
public class ArticleCreateRequest {
    private String title;
    private String content;
    private Long topicId;

 }
