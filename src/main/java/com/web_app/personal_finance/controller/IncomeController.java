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

import com.web_app.personal_finance.dto.IncomeRequest;
import com.web_app.personal_finance.model.Income;
import com.web_app.personal_finance.model.User;
import com.web_app.personal_finance.projection.IncomeProjection;
import com.web_app.personal_finance.security.JwtTokenUtil;
import com.web_app.personal_finance.service.IncomeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/income")
public class IncomeController {
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private IncomeService incomeService;

	@PostMapping
	public ResponseEntity<Income> createIncome(@Valid @RequestBody IncomeRequest incomeRequest,
			@AuthenticationPrincipal Jwt jwt) {
		User user = jwtTokenUtil.getUser(jwt);
		Income savedIncome = incomeService.save(incomeRequest, user);
		return new ResponseEntity<>(savedIncome, HttpStatus.CREATED);
	}

	@GetMapping
	public Page<IncomeProjection> getIncomes(@AuthenticationPrincipal Jwt jwt,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		Long userId = Long.parseLong(jwt.getClaim("userId"));
		return incomeService.getPaginatedIncomes(userId, page, size);
	}

	@PutMapping
	public ResponseEntity<?> updateIncome(@RequestBody IncomeRequest incomeRequest,@AuthenticationPrincipal Jwt jwt) {

	    User user = jwtTokenUtil.getUser(jwt);

	    Optional<Income> existingIncomeOpt = incomeService.findById(incomeRequest.getId());

	    if (existingIncomeOpt.isEmpty()) {
	        return new ResponseEntity<>("Income not found", HttpStatus.NOT_FOUND);
	    }

	    Income existingIncome = existingIncomeOpt.get();

	    if (!existingIncome.getUser().getId().equals(user.getId())) {
	        return new ResponseEntity<>("User not authorized to update this income entry", HttpStatus.FORBIDDEN);
	    }
	    
	    Income updatedIncome = incomeService.updateIncome(existingIncome,incomeRequest);

	    return new ResponseEntity<>(updatedIncome, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?>incomeDelete(@PathVariable Long id,@AuthenticationPrincipal Jwt jwt){
		
		Long userId = Long.parseLong(jwt.getClaim("userId"));
		
		Optional<Income> incomeOpt = incomeService.findById(id); 
		
		Income income = incomeOpt.get();
		
		if(!income.getUser().getId().equals(userId)) {
			 return new ResponseEntity<>("User not authorized to delete this IncomeSourceProjection",HttpStatus.FORBIDDEN);
		}
		
		incomeService.deleteIncomeById(id);
		
		 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}

}
