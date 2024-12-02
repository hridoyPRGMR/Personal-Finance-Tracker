package com.web_app.personal_finance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.web_app.personal_finance.dto.DebtRequest;
import com.web_app.personal_finance.filter.dto.DebtFilterDto;
import com.web_app.personal_finance.model.Debt;
import com.web_app.personal_finance.model.Expense;
import com.web_app.personal_finance.model.User;
import com.web_app.personal_finance.repository.DebtRepository;
import com.web_app.personal_finance.specification.DebtSpecification;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DebtService {
		
	private DebtRepository debtRepository;
		
	public DebtService(DebtRepository debtRepository) {
		this.debtRepository = debtRepository;
	}
	
	public Debt save(DebtRequest debRequest,User user) {
		
		Debt debt = new Debt(debRequest.getDebtType(),debRequest.getCreditor(),debRequest.getOutstandingBalance(),debRequest.getBorrowingDate(),
				debRequest.getLoanTenure(),debRequest.getInterestRate(),debRequest.getIntrestType(),debRequest.getInstallmentType(),
				debRequest.getDay(),debRequest.getDate(),debRequest.getMonth(),debRequest.getMinimumPayment(),debRequest.getNote(),user);
		
		return debtRepository.save(debt);
	}
	
	public Page<Debt>getAllDebts(Long userId,int page,int size){
		return debtRepository.findByUserId(userId,PageRequest.of(page, size));
	}
	
	public Page<Debt> getFilterDebts(Specification<Debt> specification, Pageable pageable) {
        
		return debtRepository.findAll(specification, pageable);
    }
	
	public void deleteDebtById(Long id) {
		if(debtRepository.existsById(id)) {
			debtRepository.deleteById(id);
		}
		else {
			 throw new EntityNotFoundException("Expense not found.");
		}
	}
	
	public Optional<Debt> findById(Long id) {
		return debtRepository.findById(id);
	}
	
	public List<Debt> getAllDebtsByUserId(Long userId){
		return debtRepository.findByUserId(userId);
	}
	
	public void debtDtoToDebt(DebtRequest debtRequest,Debt debt) {
		
	}
}
