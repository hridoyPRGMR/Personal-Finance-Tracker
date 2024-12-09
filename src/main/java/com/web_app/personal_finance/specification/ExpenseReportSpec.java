package com.web_app.personal_finance.specification;


import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.web_app.personal_finance.model.Expense;

import jakarta.persistence.criteria.Predicate;

public class ExpenseReportSpec {
	
	public static Specification<Expense> applyFilter(Long userId, Integer month, Integer year) {
	    
		return (root, query, criteriaBuilder) -> {
	        List<Predicate> predicates = new ArrayList<>();

	        if (userId != null) {
	            predicates.add(criteriaBuilder.equal(root.get("user").get("id"), userId));
	        }

	        if (month != null) {
	            predicates.add(criteriaBuilder.equal(criteriaBuilder.function("MONTH", Integer.class, root.get("date")), month));
	        }

	        if (year != null) {
	            predicates.add(criteriaBuilder.equal(criteriaBuilder.function("YEAR", Integer.class, root.get("date")), year));
	        }

	        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	    };
	}

	
}
