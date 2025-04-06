package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.DTO.LoginRequest;
import com.openclassrooms.mddapi.DTO.RegisterRequest;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repositiry.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public String register (RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already in use";
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            return "Username already in use";
        }
        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword()) // TODO: encrypt password later
                .build();

        userRepository.save(user);
        return "Registration successful";
    }

    public String login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail((request.getEmail()));
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if ((user.getPassword().equals(request.getPassword()))) {
                return "Login successful";
            }
        } return "Invalid credential";
    }
}
