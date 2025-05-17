package com.openclassrooms.mddapi.DTO;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String username;
    private String email;
    private String password;
}
