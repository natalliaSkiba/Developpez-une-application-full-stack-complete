package com.openclassrooms.mddapi.exception;

public class TopicNotFoundException  extends RuntimeException {
    public TopicNotFoundException(Long topicId) {
            super("Sujet non trouvé avec l'id : " + topicId);
        }
}
