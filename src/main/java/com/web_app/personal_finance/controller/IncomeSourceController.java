package com.web_app.personal_finance.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.web_app.personal_finance.model.IncomeSource;
import com.web_app.personal_finance.model.User;
import com.web_app.personal_finance.projection.IncomeSourceProjection;
import com.web_app.personal_finance.security.JwtTokenUtil;
import com.web_app.personal_finance.service.IncomeSourceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/income-sources")
public class IncomeSourceController {
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private IncomeSourceService incomeSourceService;
	
	@PostMapping
	public ResponseEntity<IncomeSource>createIncomeSource(@Valid @RequestBody IncomeSource incomeSource,@AuthenticationPrincipal Jwt jwt){
		
		System.out.println(incomeSource.toString());
		
		User user = jwtTokenUtil.getUser(jwt);
		incomeSource.setUser(user);
		IncomeSource savedIncomeSource = incomeSourceService.save(incomeSource);
		
		return new ResponseEntity<>(savedIncomeSource,HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<IncomeSource>> getIncomeSources(@AuthenticationPrincipal Jwt jwt) {
		
		Long userId = Long.parseLong(jwt.getClaim("userId")); 
		
		List<IncomeSource> incomeSources = incomeSourceService.getIncomeSourceByUserId(userId);
		
		return ResponseEntity.ok(incomeSources);
	}
	
	//get only id and income_source column
	@GetMapping("/filter")
	public ResponseEntity<List<IncomeSourceProjection>> getIncomeSourcesWithProjection(@AuthenticationPrincipal Jwt jwt){
		Long userId = Long.parseLong(jwt.getClaim("userId")); 
		
		return ResponseEntity.ok(incomeSourceService.getIncomeSourceByUserIdProjection(userId));
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<IncomeSource>> getIncomeSource(@PathVariable Long id){
		
		Optional<IncomeSource> incomeSource = incomeSourceService.findById(id);
		
		return ResponseEntity.ok(incomeSource);
		
	}
	
	@PutMapping
	public ResponseEntity<?> updateIncomeSource(@RequestBody IncomeSource incomeSource, @AuthenticationPrincipal Jwt jwt) {
	    
	    Long userId = Long.parseLong(jwt.getClaim("userId"));
	    
	    Optional<IncomeSource> existingIncomeSourceOpt = incomeSourceService.findById(incomeSource.getId());
	    
	    if (existingIncomeSourceOpt.isEmpty()) {
	        return new ResponseEntity<>("IncomeSourceProjection not found", HttpStatus.NOT_FOUND);
	    }
	    
	    IncomeSource existingIncomeSource = existingIncomeSourceOpt.get();
	    
	    if (!existingIncomeSource.getUser().getId().equals(userId)) {
	        return new ResponseEntity<>("User not authorized to update this IncomeSourceProjection", HttpStatus.FORBIDDEN);
	    }
	    
	    incomeSource.setUser(jwtTokenUtil.getUser(jwt));
	    
	    IncomeSource updatedIncomeSource = incomeSourceService.updateIncomeSource(incomeSource);
	    
	    return new ResponseEntity<>(updatedIncomeSource, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteIncomeSource(@PathVariable Long id,@AuthenticationPrincipal Jwt jwt){
		
		 Long userId = Long.parseLong(jwt.getClaim("userId"));
		 Optional<IncomeSource> incomeSourceOpt = incomeSourceService.findById(id);
		    
		 if (incomeSourceOpt.isEmpty()) {
		    return new ResponseEntity<>("IncomeSourceProjection not found", HttpStatus.NOT_FOUND);
		 }
		    
		 IncomeSource incomeSource = incomeSourceOpt.get();
		 
		 if(!incomeSource.getUser().getId().equals(userId)) {
			 return new ResponseEntity<>("User not authorized to delete this IncomeSourceProjection",HttpStatus.FORBIDDEN);
		 }
		 
		 incomeSourceService.deleteIncomeSourceById(id);
		 
		 
		 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	
}
