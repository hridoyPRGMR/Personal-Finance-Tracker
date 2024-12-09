package com.web_app.personal_finance.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web_app.personal_finance.dto.ExpenseRequest;
import com.web_app.personal_finance.model.Expense;
import com.web_app.personal_finance.model.User;
import com.web_app.personal_finance.security.JwtTokenUtil;
import com.web_app.personal_finance.service.ExpenseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {
		
	@Autowired
	private ExpenseService expenseService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	
	@PostMapping
	public ResponseEntity<?> createExpense(@Valid @RequestBody ExpenseRequest expenseReq,@AuthenticationPrincipal Jwt jwt){
		
		User user = jwtTokenUtil.getUser(jwt);
		
		Expense savedExpense = expenseService.save(expenseReq, user);
		
		return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
		
	}
	
	@GetMapping
	public Page<Expense> getAllExpense(@AuthenticationPrincipal Jwt jwt,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
		
		Long userId = Long.parseLong(jwt.getClaim("userId"));
		
		return expenseService.getExpenses(userId, page, size);
		
	}
	
	@PutMapping
	public ResponseEntity<?>updateExpense(@Valid @RequestBody ExpenseRequest expenseReq,@AuthenticationPrincipal Jwt jwt){
		
		User user = jwtTokenUtil.getUser(jwt);

		Optional<Expense>expenseOp = expenseService.findById(expenseReq.getId());
		
		if(expenseOp.isEmpty()) {
			return new ResponseEntity<>("Expense not found", HttpStatus.NOT_FOUND);
		}
		
		Expense existingExpense = expenseOp.get();
		
	    if (!existingExpense.getUser().getId().equals(user.getId())) {
	        return new ResponseEntity<>("User not authorized to update this expense entry", HttpStatus.FORBIDDEN);
	    }
	    	
	    Expense updatedExpense = expenseService.updateExpense(existingExpense,expenseReq);
	    
	    return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteExpense(@PathVariable Long id,@AuthenticationPrincipal Jwt jwt){
		
		Long userId = Long.parseLong(jwt.getClaim("userId"));
		
		Optional<Expense> expenseOp = expenseService.findById(id);
		
		Expense expense = expenseOp.get();
		
		if(!expense.getUser().getId().equals(userId)) {
			 return new ResponseEntity<>("User not authorized to delete this IncomeSourceProjection",HttpStatus.FORBIDDEN);
		}
		
		expenseService.deleteExpenseById(id);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
}
