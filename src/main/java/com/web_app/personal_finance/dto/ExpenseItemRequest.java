package com.web_app.personal_finance.dto;

import jakarta.validation.constraints.NotNull;

public class ExpenseItemRequest {

    @NotNull(message = "Amount is required")
    private Double amount;

    @NotNull(message = "Expense description is required")
    private String expenseOn;

    // Getters and setters
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
}
