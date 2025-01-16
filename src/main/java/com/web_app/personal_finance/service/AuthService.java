package com.web_app.personal_finance.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.web_app.personal_finance.dto.SignUpRequest;
import com.web_app.personal_finance.model.User;
import com.web_app.personal_finance.model.UserRole;
import com.web_app.personal_finance.repository.RoleRepository;
import com.web_app.personal_finance.repository.UserRepository;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public User saveUser(SignUpRequest request) {

    	if (userRepository.findByUsername(request.getEmail()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
    	
        User user = new User();
        user.setUsername(request.getEmail());
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        // Fetch the default "USER" role from the database
        UserRole userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Default role USER not found"));
        
        user.setRoles(Collections.singleton(userRole));
        user.setEnabled(true);
        
        return userRepository.save(user);
	}

}
