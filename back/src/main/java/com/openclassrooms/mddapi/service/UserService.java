package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.DTO.UpdateUserRequest;
import com.openclassrooms.mddapi.model.User;

public interface UserService {
    User getUserById(Long id);
    User updateUser(long id, UpdateUserRequest request) ;
}
