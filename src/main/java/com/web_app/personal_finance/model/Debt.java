package com.web_app.personal_finance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name="debts")
public class Debt {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private byte debtType;
	
	private String creditor;
	
	private Double outstandingBalance;
	
	private LocalDate borrowingDate;
	
	private byte loanTenure;
	
	private Double interestRate;
	
	private byte interestType;
	
	private byte installmentType;
	
	private byte day;
	
	private byte date;
	
	private byte month;
	
	private double minimumPayment;
	
	private String note;
	
	@ManyToOne
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	
	@Column(name="created_at",updatable=false)
	private LocalDateTime createdAt;
	
	@Column(name="updated_at")
	private LocalDateTime updatedAt;
	
	public Debt() {}
	
	public Debt(byte debtType, String creditor, Double outstandingBalance, LocalDate borrowingDate, byte loanTenure,
			Double interestRate, byte interestType, byte installmentType, byte day, byte date, byte month,
			double minimumPayment, String note, User user) {
		super();
		this.debtType = debtType;
		this.creditor = creditor;
		this.outstandingBalance = outstandingBalance;
		this.borrowingDate = borrowingDate;
		this.loanTenure = loanTenure;
		this.interestRate = interestRate;
		this.interestType = interestType;
		this.installmentType = installmentType;
		this.day = day;
		this.date = date;
		this.month = month;
		this.minimumPayment = minimumPayment;
		this.note = note;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public byte getDebtType() {
		return debtType;
	}

	public void setDebtType(byte debtType) {
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

	public void setInterestType(byte interestType) {
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}


	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
	}
	
	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
}
