package com.solutrix.salon.controller;

import com.solutrix.salon.dto.LoginDTO;
import com.solutrix.salon.dto.JwtAuthResponse;
import com.solutrix.salon.service.impl.AuthServiceImpl;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

	
    private PasswordEncoder passwordEncoder;
    private AuthServiceImpl authService;
    

    // Build Login REST API
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDTO loginDto){
    	System.out.println(loginDto.getUsername()+"   -   "+loginDto.getPassword());
        String token = authService.login(loginDto);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }
    @GetMapping("/test")
    public void test(@RequestBody Map<String, String> map){
        System.out.println(passwordEncoder.encode(map.get("password")));
    }
}
