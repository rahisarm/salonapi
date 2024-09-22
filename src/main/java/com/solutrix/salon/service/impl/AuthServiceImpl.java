package com.solutrix.salon.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.solutrix.salon.component.JwtTokenProvider;
import com.solutrix.salon.dto.LoginDTO;
import com.solutrix.salon.service.AuthService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

	 private AuthenticationManager authenticationManager;
	 
	 private JwtTokenProvider jwtTokenProvider;

	    @Override
	    public String login(LoginDTO loginDto) {

	        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
	                loginDto.getUsername(),
	                loginDto.getPassword()
	        ));

	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        String token = jwtTokenProvider.generateToken(authentication);

	        return token;
	    }


}
