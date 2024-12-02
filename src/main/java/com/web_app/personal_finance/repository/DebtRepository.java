package com.web_app.personal_finance.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.web_app.personal_finance.model.Debt;

public interface DebtRepository extends JpaRepository<Debt,Long>,JpaSpecificationExecutor<Debt>{
	
	Page<Debt>findByUserId(Long id,Pageable pageable);
	
	List<Debt> findByUserId(Long userId);
}
