package com.web_app.personal_finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web_app.personal_finance.model.IncomeSource;
import com.web_app.personal_finance.projection.IncomeSourceProjection;

public interface IncomeSourceRepository extends JpaRepository<IncomeSource, Long> {
    
	
	List<IncomeSource> findByUserId(Long id);
	
	 List<IncomeSourceProjection> findProjectionsByUserId(Long userId);
	
	
}
