package com.solutrix.salon.service;

import com.solutrix.salon.dto.LoginDTO;

import org.springframework.stereotype.Service;

public interface AuthService {

	String login(LoginDTO loginDto);
}
