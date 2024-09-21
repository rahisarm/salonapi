package com.solutrix.salon.controller;

import com.solutrix.salon.dto.AuthRequest;
import com.solutrix.salon.dto.AuthResponse;
import com.solutrix.salon.service.JwtAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private JwtAuthenticationManager jwtAuthenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            String token = jwtAuthenticationManager.authenticate(authRequest.getUsername(), authRequest.getPassword());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
