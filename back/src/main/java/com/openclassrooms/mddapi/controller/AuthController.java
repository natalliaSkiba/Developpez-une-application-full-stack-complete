package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.DTO.JwtResponse;
import com.openclassrooms.mddapi.DTO.LoginRequest;
import com.openclassrooms.mddapi.DTO.RegisterRequest;
import com.openclassrooms.mddapi.config.JwtUtil;
import com.openclassrooms.mddapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller responsible for handling user authentication requests.
 *
 * Provides endpoints for user registration and login.
 */

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") // allow Angular frontend
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    /**
     * Registers a new user with the provided credentials.
     *
     * @param request The user's registration details.
     * @return A success message if registration is successful.
     */
    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account and stores their credentials"
    )
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        String result = authService.register(request);
        return ResponseEntity.ok(result);
    }

    /**
     * Authenticates a user and returns a JWT token.
     *
     * @param request The user's login credentials.
     * @return A JWT token wrapped in a JwtResponse.
     */
    @Operation(
            summary = "Login and receive JWT token",
            description = "Authenticates the user and returns a signed JWT"
    )
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
