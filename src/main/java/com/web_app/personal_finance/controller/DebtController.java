package com.web_app.personal_finance.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
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

import com.web_app.personal_finance.dto.DebtRequest;
import com.web_app.personal_finance.filter.dto.DebtFilterDto;
import com.web_app.personal_finance.model.Debt;
import com.web_app.personal_finance.model.User;
import com.web_app.personal_finance.security.JwtTokenUtil;
import com.web_app.personal_finance.service.DebtService;
import com.web_app.personal_finance.specification.DebtSpecification;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/debt")
public class DebtController {
		
	@Autowired
	private DebtService debtService;
	
	@Autowired
	private final JwtTokenUtil jwtTokenUtil;
	
	public DebtController(DebtService debtService,JwtTokenUtil jwtTokenUtil) {
		this.debtService = debtService;
		this.jwtTokenUtil = jwtTokenUtil;
	}
	
	@PostMapping
	public ResponseEntity<?> createDebt(@Valid @RequestBody DebtRequest debtRequest, @AuthenticationPrincipal Jwt jwt){
			
		User user = jwtTokenUtil.getUser(jwt);
		
		Debt debt = debtService.save(debtRequest, user);
		
		return new ResponseEntity<>(debt, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<?> updateDebt(@Valid @RequestBody DebtRequest debtRequest,@AuthenticationPrincipal Jwt jwt){

		User user = jwtTokenUtil.getUser(jwt);

	    Debt updatedDebt = debtService.updateDebt(debtRequest.getId(),debtRequest);

		return new ResponseEntity<>(updatedDebt, HttpStatus.OK);
	}

	
	@GetMapping
	public Page<Debt> getAllDebts(@AuthenticationPrincipal Jwt jwt,@RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="10") int size){
		
		Long userId = Long.parseLong(jwt.getClaim("userId"));
		
		return debtService.getAllDebts(userId,page,size);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteDebt(@PathVariable Long id,@AuthenticationPrincipal Jwt jwt){
		

		Long userId = Long.parseLong(jwt.getClaim("userId"));
		
		Optional<Debt> debtOp = debtService.findById(id);
		
		debtService.deleteDebtById(id);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}
	
	@PostMapping("/filter")
	public Page<Debt> getFilterDebts(
	    @Valid @RequestBody DebtFilterDto filter,
	    @PageableDefault(sort = "borrowingDate", direction = Sort.Direction.ASC) Pageable pageable,
	    @AuthenticationPrincipal Jwt jwt
	) {
	    Long userId = Long.parseLong(jwt.getClaim("userId"));
	    Specification<Debt> specification = DebtSpecification.applyFilter(filter, userId);
	    
	    return debtService.getFilterDebts(specification, pageable);
	}
	
}
