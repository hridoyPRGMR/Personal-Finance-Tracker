package com.web_app.personal_finance.filter.dto;

import jakarta.validation.constraints.Size;

public class DebtFilterDto {
	
	private byte borrowingDate;
	
	@Size(max=100,message="Creditor must be less than 100 character.")
	private String creditor;
	
	private byte debtType;
	
	private byte installmentType;

	public byte getBorrowingDate() {
		return borrowingDate;
	}

	public void setBorrowingDate(byte borrowingDate) {
		this.borrowingDate = borrowingDate;
	}

	public String getCreditor() {
		return creditor;
	}

	public void setCreditor(String creditor) {
		this.creditor = creditor;
	}

	public byte getDebtType() {
		return debtType;
	}

	public void setDebtType(byte debtType) {
		this.debtType = debtType;
	}

	public byte getInstallmentType() {
		return installmentType;
	}

	public void setInstallmentType(byte installmentType) {
		this.installmentType = installmentType;
	}
	
	
}
