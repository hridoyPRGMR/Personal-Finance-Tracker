package com.web_app.personal_finance.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "expense_item")
public class ExpenseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Double amount;

    private String expenseOn;
    
    @ManyToOne
    @JoinColumn(name = "expense_id", nullable = false)
    @JsonBackReference
    private Expense expense;
    
    public ExpenseItem() {
    	
    }
    
    public ExpenseItem(Double amount, String expenseOn) {
		super();
		this.amount = amount;
		this.expenseOn = expenseOn;
	}

	// Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getExpenseOn() {
        return expenseOn;
    }

    public void setExpenseOn(String expenseOn) {
        this.expenseOn = expenseOn;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }
}
