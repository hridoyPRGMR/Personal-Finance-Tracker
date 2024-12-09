package com.web_app.personal_finance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.web_app.personal_finance.dto.ExpenseRequest;
import com.web_app.personal_finance.model.Expense;
import com.web_app.personal_finance.model.ExpenseItem;
import com.web_app.personal_finance.model.User;
import com.web_app.personal_finance.repository.ExpenseRepository;
import com.web_app.personal_finance.specification.ExpenseReportSpec;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ExpenseService {
	
	@Autowired
	private ExpenseRepository expenseRepository;

	public Expense save(ExpenseRequest expenseReq, User user) {

		Expense expense = new Expense(expenseReq.getDate(),expenseReq.getNote(),user);
		
		mapDtoToExpense(expenseReq,expense);

		return expenseRepository.save(expense);

	}

	public Page<Expense> getExpenses(Long userId, int page, int size) {
		return expenseRepository.findByUserId(userId, PageRequest.of(page, size));

	}
	
	public List<Expense> getAllExpenses(Long userId){
		return expenseRepository.findByUserId(userId);
	}
		
	public List<Expense> getAllFilteredExpenses(Long userId,Integer month,Integer year){
		
		Specification<Expense> spec= ExpenseReportSpec.applyFilter(userId, month, year);
		
		return expenseRepository.findAll(spec);
		
	}
	
	public Optional<Expense> findById(Long id) {
		return expenseRepository.findById(id);
	}

	public Expense updateExpense(Expense existingExpense, ExpenseRequest expenseReq) {

		mapDtoToExpense(expenseReq, existingExpense);

		return expenseRepository.save(existingExpense);
	}
	
	public void deleteExpenseById(Long id) {
		if(expenseRepository.existsById(id)) {
			expenseRepository.deleteById(id);
		}
		else {
			 throw new EntityNotFoundException("Expense not found.");
		}
	}

	private void mapDtoToExpense(ExpenseRequest dto, Expense expense) {

		expense.setDate(dto.getDate());
		expense.setNote(dto.getNote());

		if (dto.getExpenses() != null) {

			// Clear existing items if updating
			if (expense.getExpenses() != null) {
				expense.getExpenses().clear();
			}

			dto.getExpenses().forEach(item -> {
				ExpenseItem expenseItem = new ExpenseItem(item.getAmount(), item.getExpenseOn());
				expenseItem.setExpense(expense);
				expense.getExpenses().add(expenseItem);
			});
		}

	}

}