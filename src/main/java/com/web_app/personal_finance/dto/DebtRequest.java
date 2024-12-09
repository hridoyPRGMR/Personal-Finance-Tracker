package com.web_app.personal_finance.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public class DebtRequest {
	
	private Long id;

	@NotNull(message="Debt Type is required.")
	private byte debtType;
	
	@NotNull(message="Creditor is required.")
	@Size(min=3,max=250,message="Creditor must be in between 3 to 250 character")
	private String creditor;
	
	@NotNull(message="OutStanding balance is required.")
	private Double outstandingBalance;
	
	@PastOrPresent(message = "The registration date cannot be in the future.")
	private LocalDate borrowingDate;
	
	@NotNull(message="Loan Tenure is required.")
	private byte loanTenure;
	
	@NotNull(message="Interest rate is required.")
	private Double interestRate;
	
	@NotNull(message="Interest type is required.")
	private byte interestType;
	
	@NotNull(message="Installment type is required.")
	private byte installmentType;
	
	private byte day;

	private byte date;

	private byte month;
	
	@NotNull(message="Minimum payment is required.")
	@DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0.")
	private double minimumPayment;
	
	
	@Size(max=250,message="Note must be not more than 250 character.")
	private String note;

	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public byte getDebtType() {
		return debtType;
	}

	public void setDebType(byte debtType) {
		this.debtType = debtType;
	}

	public String getCreditor() {
		return creditor;
	}

	public void setCreditor(String creditor) {
		this.creditor = creditor;
	}

	public Double getOutstandingBalance() {
		return outstandingBalance;
	}

	public void setOutstandingBalance(Double outstandingBalance) {
		this.outstandingBalance = outstandingBalance;
	}

	public LocalDate getBorrowingDate() {
		return borrowingDate;
	}

	public void setBorrowingDate(LocalDate borrowingDate) {
		this.borrowingDate = borrowingDate;
	}

	public byte getLoanTenure() {
		return loanTenure;
	}

	public void setLoanTenure(byte loanTenure) {
		this.loanTenure = loanTenure;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public byte getInterestType() {
		return interestType;
	}

	public void setIntrestType(byte interestType) {
		this.interestType = interestType;
	}

	public byte getInstallmentType() {
		return installmentType;
	}

	public void setInstallmentType(byte installmentType) {
		this.installmentType = installmentType;
	}

	public byte getDay() {
		return day;
	}

	public void setDay(byte day) {
		this.day = day;
	}

	public byte getDate() {
		return date;
	}

	public void setDate(byte date) {
		this.date = date;
	}

	public byte getMonth() {
		return month;
	}

	public void setMonth(byte month) {
		this.month = month;
	}

	public double getMinimumPayment() {
		return minimumPayment;
	}

	public void setMinimumPayment(double minimumPayment) {
		this.minimumPayment = minimumPayment;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
