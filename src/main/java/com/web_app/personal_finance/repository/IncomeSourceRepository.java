package com.web_app.personal_finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web_app.personal_finance.model.IncomeSource;

public interface IncomeSourceRepository extends JpaRepository<IncomeSource, Long> {
    // JpaRepository already provides findById, so this is not necessary
    // Optional<IncomeSource> findById(int id);
	
	List<IncomeSource> findByUserId(Long id);
	
	
}
