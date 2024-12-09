package com.web_app.personal_finance.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.web_app.personal_finance.model.Income;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface IncomeReportSpec {

	public static Specification<Income> applyFilter(Long userId,Integer year, Long incomeSource) {

		return (Root<Income> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {

			 List<Predicate> predicates = new ArrayList<>();
			 
			if(userId != null) {
				predicates.add(criteriaBuilder.equal(root.get("user").get("id"),userId));
			}
			 
			if (year != null) {
				predicates.add(criteriaBuilder.equal(
					criteriaBuilder.function("YEAR", Integer.class, root.get("date")), year
				));
			}
			
			if (incomeSource != null && incomeSource != 0) {
                predicates.add(criteriaBuilder.equal(root.get("incomeSource").get("id"),incomeSource));
            }

			 return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};

	}

}
