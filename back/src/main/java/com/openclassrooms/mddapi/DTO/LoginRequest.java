package com.openclassrooms.mddapi.DTO;

import lombok.Data;

@Data
public class LoginRequest {
    private String identifier;
    private String password;
}
