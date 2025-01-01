package com.web_app.personal_finance.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ExpenseRequest {

    private Long id;
    
    @NotNull(message="Currency is required.")
    @Size(max=3)
    private String currency;
    
    @NotNull(message = "Date is required")
    private LocalDate date;

    @Size(max = 250, message = "Note can be up to 250 characters")
    private String note;

    @NotNull(message = "Expense items are required")
    private List<ExpenseItemRequest> expenses;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
    
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<ExpenseItemRequest> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpenseItemRequest> expenses) {
        this.expenses = expenses;
    }
}
