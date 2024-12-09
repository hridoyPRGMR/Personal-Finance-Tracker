package com.web_app.personal_finance.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.web_app.personal_finance.model.Income;
import com.web_app.personal_finance.projection.IncomeProjection;

public interface IncomeRepository extends JpaRepository<Income,Long>,JpaSpecificationExecutor<Income>{
	
	 Page<IncomeProjection> findByUserId(Long userId, Pageable pageable);
	 
	 List<Income> findByUserId(Long userId);
	 
}
