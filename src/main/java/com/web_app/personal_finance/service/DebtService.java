package com.web_app.personal_finance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.web_app.personal_finance.dto.DebtRequest;
import com.web_app.personal_finance.model.Debt;
import com.web_app.personal_finance.model.User;
import com.web_app.personal_finance.repository.DebtRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DebtService {
	
	@Autowired
    private DebtRepository debtRepository;

    public Debt save(DebtRequest debRequest, User user) {

        Debt debt = new Debt(debRequest.getDebtType(), debRequest.getCreditor(), debRequest.getOutstandingBalance(), debRequest.getBorrowingDate(),
                debRequest.getLoanTenure(), debRequest.getInterestRate(), debRequest.getInterestType(), debRequest.getInstallmentType(),
                debRequest.getDay(), debRequest.getDate(), debRequest.getMonth(), debRequest.getMinimumPayment(), debRequest.getNote(), user);

        return debtRepository.save(debt);
    }

    public Debt updateDebt(Long id, DebtRequest debtRequest) {
        return debtRepository.findById(id).map(existingDebt -> {
            return update(debtRequest, existingDebt); 
        }).orElseThrow(() -> new RuntimeException("Debt not found with id " + id));
    }

    public Page<Debt> getAllDebts(Long userId, int page, int size) {
        return debtRepository.findByUserId(userId, PageRequest.of(page, size));
    }

    public Page<Debt> getFilterDebts(Specification<Debt> specification, Pageable pageable) {

        return debtRepository.findAll(specification, pageable);
    }

    public void deleteDebtById(Long id) {
        if (debtRepository.existsById(id)) {
            debtRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Expense not found.");
        }
    }

    public Optional<Debt> findById(Long id) {
        return debtRepository.findById(id);
    }

    public List<Debt> getAllDebtsByUserId(Long userId) {
        return debtRepository.findByUserId(userId);
    }

	private Debt update(DebtRequest debtRequest, Debt existingDebt) {
        // Map fields from DebtRequest to existingDebt
        existingDebt.setDebtType(debtRequest.getDebtType());
        existingDebt.setCreditor(debtRequest.getCreditor());
        existingDebt.setOutstandingBalance(debtRequest.getOutstandingBalance());
        existingDebt.setBorrowingDate(debtRequest.getBorrowingDate());
        existingDebt.setLoanTenure(debtRequest.getLoanTenure());
        existingDebt.setInterestRate(debtRequest.getInterestRate());
        existingDebt.setInterestType(debtRequest.getInterestType());
        existingDebt.setInstallmentType(debtRequest.getInstallmentType());
        existingDebt.setDay(debtRequest.getDay());
        existingDebt.setDate(debtRequest.getDate());
        existingDebt.setMonth(debtRequest.getMonth());
        existingDebt.setMinimumPayment(debtRequest.getMinimumPayment());
        existingDebt.setNote(debtRequest.getNote());

        // Save and return the updated entity
        return debtRepository.save(existingDebt);
    }

}
