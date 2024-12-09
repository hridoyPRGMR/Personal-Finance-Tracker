package com.web_app.personal_finance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web_app.personal_finance.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	
	Optional<User>findByUsername(String username);
	
}
