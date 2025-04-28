package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.DTO.UpdateUserRequest;
import com.openclassrooms.mddapi.model.User;

public interface UserService {
    User getUserByUsername(String username);
    User updateUser(String username, UpdateUserRequest request) ;
}
