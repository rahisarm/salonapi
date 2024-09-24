package com.solutrix.salon.service.impl;

import com.solutrix.salon.entity.User;
import com.solutrix.salon.exception.ResourceNotFoundException;
import com.solutrix.salon.repository.UserRepo;
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

	private final UserRepo userRepo;
	private AuthenticationManager authenticationManager;
	 
	 private JwtTokenProvider jwtTokenProvider;

	    @Override
	    public String login(LoginDTO loginDto) {

	        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
	                loginDto.getUsername(),
	                loginDto.getPassword()
	        ));

	        SecurityContextHolder.getContext().setAuthentication(authentication);

			User user = userRepo.findByUsername(loginDto.getUsername())
					.orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + loginDto.getUsername()));
			int userdocno = user.getDocno();  // Assuming User entity has a `docno` field

			String token = jwtTokenProvider.generateToken(authentication,userdocno);

	        return token;
	    }


}
