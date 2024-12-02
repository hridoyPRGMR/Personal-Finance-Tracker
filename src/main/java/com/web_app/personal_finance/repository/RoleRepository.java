package com.web_app.personal_finance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web_app.personal_finance.model.UserRole;

public interface RoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByName(String name);
}