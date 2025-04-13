package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.DTO.LoginRequest;
import com.openclassrooms.mddapi.DTO.RegisterRequest;
import com.openclassrooms.mddapi.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") // allow Angular frontend
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        String result = authService.register(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public  ResponseEntity<String> login (@RequestBody LoginRequest request){
        String result = authService.login(request);
        return  ResponseEntity.ok(result);
    }
}
