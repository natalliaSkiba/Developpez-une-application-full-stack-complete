package com.openclassrooms.mddapi.DTO;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class RegisterRequest {

    @NotBlank(message = "Le nom d'utilisateur ne doit pas être vide")
    private String username;

    @NotBlank(message = "L'adresse e-mail ne doit pas être vide")
    @Email(message = "L'adresse e-mail n'est pas valide")
    private String email;

    @NotBlank(message = "Le mot de passe ne doit pas être vide")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$",
            message = "Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial")
    private String password;
}
