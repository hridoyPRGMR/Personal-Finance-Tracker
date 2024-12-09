package com.web_app.personal_finance.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.web_app.personal_finance.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense,Long>,JpaSpecificationExecutor<Expense> {
	
	Page<Expense>findByUserId(Long id,Pageable pageable);
	
	List<Expense>findByUserId(Long userId);
}
