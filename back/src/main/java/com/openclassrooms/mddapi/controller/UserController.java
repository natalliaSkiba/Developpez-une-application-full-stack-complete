package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.DTO.UpdateUserRequest;
import com.openclassrooms.mddapi.config.JwtUtil;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.CustomUserDetailsService;
import com.openclassrooms.mddapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * Controller responsible for managing user profile operations.
 *
 * Provides endpoints to retrieve and update the current authenticated user's profile.
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    private final CustomUserDetailsService customUserDetailsService;


    /**
     * Returns the profile data of the currently authenticated user.
     *
     * @return the User object of the logged-in user
     */
    @GetMapping("/profile")
    public ResponseEntity<User> getUserById() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(userService.getUserByUsername(currentUsername));
    }

    /**
     * Updates the current user's username, email, or password.
     * Returns a new JWT token if the update is successful, or an error message otherwise.
     *
     * @param request the new user data
     * @return a map containing either the new token or an error message
     */
    @PutMapping("/profile")
    public ResponseEntity<Map<String, String>> updateUser(@Valid @RequestBody UpdateUserRequest request) {
        try {
            String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            System.out.println(">> Authenticated username: " + currentUsername);

            User updatedUser = userService.updateUser(currentUsername, request);
            System.out.println(">> Updated user: " + updatedUser.getUsername());

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(updatedUser.getUsername());
            System.out.println(">> Loaded user details");

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authToken);

            String newJwt = jwtUtil.generateToken(authToken);
            System.out.println(">> New JWT: " + newJwt);

            return ResponseEntity.ok(Map.of("token", newJwt));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("message", "Erreur interne: " + e.getMessage()));
        }
    }
}


