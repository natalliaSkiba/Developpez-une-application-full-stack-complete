package com.openclassrooms.mddapi.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("Utilisateur non trouvé avec l'id : " + userId);
    }
}
