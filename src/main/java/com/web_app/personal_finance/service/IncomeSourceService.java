package com.web_app.personal_finance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web_app.personal_finance.model.IncomeSource;
import com.web_app.personal_finance.projection.IncomeSourceProjection;
import com.web_app.personal_finance.repository.IncomeSourceRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class IncomeSourceService {
	
	@Autowired
	private IncomeSourceRepository incomeSourceRepository;
	
	
	public IncomeSource save(IncomeSource incomeSource) {
		return incomeSourceRepository.save(incomeSource);
	}
	
	public List<IncomeSource>getIncomeSourceByUserId(Long userId){
		
		return incomeSourceRepository.findByUserId(userId);	
	}
	
	public List<IncomeSourceProjection> getIncomeSourceByUserIdProjection(Long userId){
		
		return incomeSourceRepository.findProjectionsByUserId(userId);	
	}
	
	
	
	public Optional<IncomeSource> findById(Long id) {
		return incomeSourceRepository.findById(id);
	}
	
	public IncomeSource updateIncomeSource(IncomeSource incomeSource) {
		return incomeSourceRepository.save(incomeSource);
	}
	
	public void deleteIncomeSourceById(Long id) {
	    if (incomeSourceRepository.existsById(id)) {
	        incomeSourceRepository.deleteById(id);
	    } else {
	        throw new EntityNotFoundException("IncomeSourceProjection not found with ID: " + id);
	    }
	}

	
}
