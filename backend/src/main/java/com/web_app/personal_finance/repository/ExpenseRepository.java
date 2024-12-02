package com.web_app.personal_finance.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.web_app.personal_finance.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {
	
	Page<Expense>findByUserId(Long id,Pageable pageable);
	
	List<Expense>findByUserId(Long userId);
}
