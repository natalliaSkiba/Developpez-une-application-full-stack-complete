package com.openclassrooms.mddapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopicResponse {
    private Long id;
    private String name;
    private String description;
    private boolean isSubscribed;
}
