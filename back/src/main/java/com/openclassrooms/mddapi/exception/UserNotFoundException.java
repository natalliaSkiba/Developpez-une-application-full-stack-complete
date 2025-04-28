package com.openclassrooms.mddapi.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("Utilisateur non trouvé avec l'id : " + username);
    }
}
