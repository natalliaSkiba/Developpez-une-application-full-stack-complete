package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.DTO.UpdateUserRequest;
import com.openclassrooms.mddapi.exception.UserNotFoundException;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repositiry.UserRepository;
import com.openclassrooms.mddapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
    }

    @Override
    public User updateUser(long id, UpdateUserRequest request) {
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            if (!request.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$")) {
                throw new IllegalArgumentException("Le mot de passe ne respecte pas les critères de sécurité.");
            }
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        return userRepository.save(user);
    }
}
