package com.web_app.personal_finance.security;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.web_app.personal_finance.model.User;



@Component
public class JwtTokenUtil {
	
	
	public User getUser(Jwt jwt) {
		
		Long userId = Long.parseLong(jwt.getClaim("userId"));
		String username = jwt.getSubject();
		
		User user = new User();
		user.setId(userId);
		user.setUsername(username);
		
		return user;
	}
	
}


