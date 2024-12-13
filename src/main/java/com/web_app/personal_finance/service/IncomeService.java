package com.web_app.personal_finance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

	public Page<IncomeProjection> getPaginatedIncomes(Long userId,Integer sortby, Integer page, Integer size) {
		
		Sort sort;
		
		switch(sortby) {
			case 1:
				sort = Sort.by(Sort.Direction.ASC,"income");
				break;
			case 2:
				sort = Sort.by(Sort.Direction.DESC,"income");
				break;
			case 3:
				sort = Sort.by(Sort.Direction.ASC,"date");
				break;
			case 4:
				sort = Sort.by(Sort.Direction.DESC,"date");
				break;
			default:
				sort = Sort.unsorted();
		}
		
		 PageRequest pageRequest = PageRequest.of(page, size, sort);
		
		return incomeRepository.findByUserId(userId, pageRequest);
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
