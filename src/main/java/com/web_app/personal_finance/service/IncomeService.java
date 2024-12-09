package com.web_app.personal_finance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.web_app.personal_finance.dto.IncomeRequest;
import com.web_app.personal_finance.model.Income;
import com.web_app.personal_finance.model.IncomeSource;
import com.web_app.personal_finance.model.User;
import com.web_app.personal_finance.projection.IncomeProjection;
import com.web_app.personal_finance.repository.IncomeRepository;
import com.web_app.personal_finance.repository.IncomeSourceRepository;
import com.web_app.personal_finance.specification.IncomeReportSpec;

import jakarta.persistence.EntityNotFoundException;

@Service
public class IncomeService {
	
	@Autowired
	private IncomeRepository incomeRepository;
	@Autowired
	private IncomeSourceRepository incomeSourceRepository;

	public Income save(IncomeRequest incomeRequest, User user) {

		IncomeSource incomeSource = incomeSourceRepository.findById(incomeRequest.getIncome_source())
				.orElseThrow(() -> new RuntimeException("Income source not found"));

		Income income = new Income(incomeRequest, user, incomeSource);

		return incomeRepository.save(income);
	}

	public Page<IncomeProjection> getPaginatedIncomes(Long userId, int page, int size) {
		return incomeRepository.findByUserId(userId, PageRequest.of(page, size));
	}
	
	public List<Income> getAllIncome(Long userId){
		return incomeRepository.findByUserId(userId);
	}
		
	public List<Income> getAllFilteredIncome(Long userId,Integer year,Long incomeSource){
		
		Specification<Income>spec = IncomeReportSpec.applyFilter(userId,year, incomeSource);
		
		return incomeRepository.findAll(spec);
		
	}
	
	public Optional<Income> findById(Long id){
		return incomeRepository.findById(id);
	}
	
	public Income updateIncome(Income existingIncome,IncomeRequest incomeRequest) {
		
		existingIncome.setIncome(incomeRequest.getIncome());
	    existingIncome.setDate(incomeRequest.getDate());
	    existingIncome.setDescription(incomeRequest.getDescription());
	    
		return incomeRepository.save(existingIncome);
	}
	
	public void deleteIncomeById(Long id) {
		if(incomeRepository.existsById(id)) {
			incomeRepository.deleteById(id);
		}
		else {
			 throw new EntityNotFoundException("Income not found with ID: " + id);
		}
	}

}
