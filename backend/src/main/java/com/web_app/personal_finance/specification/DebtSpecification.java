package com.web_app.personal_finance.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.web_app.personal_finance.filter.dto.DebtFilterDto;
import com.web_app.personal_finance.model.Debt;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class DebtSpecification {

    public static Specification<Debt> applyFilter(DebtFilterDto filter, Long userId) {

        return (Root<Debt> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (userId != null) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), userId));
            }

            if (filter.getDebtType() != 0 && filter.getDebtType() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("debtType"), filter.getDebtType()));
            }

            if (filter.getInstallmentType() != 0 && filter.getInstallmentType() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("installmentType"), filter.getInstallmentType()));
            }

            if (filter.getBorrowingDate() != 0) {
                if (filter.getBorrowingDate() == 1) {
                    query.orderBy(criteriaBuilder.asc(root.get("borrowingDate")));
                } else if (filter.getBorrowingDate() == 2) {
                    query.orderBy(criteriaBuilder.desc(root.get("borrowingDate")));
                }
            }

            if (filter.getCreditor() != null && !filter.getCreditor().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("creditor")),
                    "%" + filter.getCreditor().toLowerCase() + "%"
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
