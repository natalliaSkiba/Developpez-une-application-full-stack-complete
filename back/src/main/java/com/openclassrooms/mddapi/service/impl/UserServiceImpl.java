package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.DTO.UpdateUserRequest;
import com.openclassrooms.mddapi.exception.UserNotFoundException;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repositiry.UserRepository;
import com.openclassrooms.mddapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing user accounts.
 * <p>
 * Provides methods to retrieve a user by username and to update user profile information,
 * including username, email, and password.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    /**
     * Updates the profile information of the current user, including username, email, and optionally password.
     * <p>
     * Before updating, this method checks:
     * <ul>
     *     <li>If the new username is different and already taken — throws an error</li>
     *     <li>If the new email is different and already taken — throws an error</li>
     *     <li>If the new password is present — validates its strength and encodes it</li>
     * </ul>
     *
     * @param username the current authenticated username (extracted from token)
     * @param request  the new profile data (username, email, optional password)
     * @return the updated User entity
     * @throws UserNotFoundException    if the user is not found
     * @throws IllegalArgumentException if the new username/email is already used, or password is invalid
     */
    @Override
    public User updateUser(String username, UpdateUserRequest request) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        if (!user.getUsername().equals(request.getUsername()) &&
                userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Ce nom d'utilisateur est déjà utilisé.");
        }
        user.setUsername(request.getUsername());

        if (!user.getEmail().equals(request.getEmail()) &&
                userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé.");
        }
        user.setEmail(request.getEmail());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            if (!request.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$")) {
                throw new IllegalArgumentException("Le mot de passe ne respecte pas les critères de sécurité.");
            }
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
