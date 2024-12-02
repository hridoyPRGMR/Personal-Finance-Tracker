package com.web_app.personal_finance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.web_app.personal_finance.dto.ExpenseRequest;
import com.web_app.personal_finance.model.Expense;
import com.web_app.personal_finance.model.ExpenseItem;
import com.web_app.personal_finance.model.User;
import com.web_app.personal_finance.repository.ExpenseRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class ExpenseService {

	private ExpenseRepository expenseRepository;

	public ExpenseService(ExpenseRepository expenseRepository) {
		this.expenseRepository = expenseRepository;
	}

	public Expense save(ExpenseRequest expenseReq, User user) {

		Expense expense = new Expense(expenseReq.getDate(),expenseReq.getNote(),expenseReq.getCurrency(),user);
		
		mapDtoToExpense(expenseReq,expense);
		
//		List<ExpenseItem> data = new ArrayList<ExpenseItem>();
//		
//		expenseReq.getExpenses().forEach(item -> {
//			ExpenseItem expenseItem = new ExpenseItem(item.getAmount(), item.getExpenseOn());
//			expenseItem.setExpense(expense);
//			data.add(expenseItem);
//		});
//		
//		expense.setExpenses(data);

		return expenseRepository.save(expense);

	}

	public Page<Expense> getExpenses(Long userId, int page, int size) {
		return expenseRepository.findByUserId(userId, PageRequest.of(page, size));

	}
	
	public List<Expense> getAllExpenses(Long userId){
		return expenseRepository.findByUserId(userId);
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
		expense.setCurrency(dto.getCurrency());

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