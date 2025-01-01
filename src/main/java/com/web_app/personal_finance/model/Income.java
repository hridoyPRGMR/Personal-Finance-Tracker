package com.web_app.personal_finance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.web_app.personal_finance.dto.IncomeRequest;

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
@Table(name="income")
public class Income {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "income_source_id", nullable = false)
    private IncomeSource incomeSource;

    private Double income;
    
    private String currency;
    
    private LocalDate date;
    
    @Column(length = 250)
    private String description;
	
    public Income() {}
	
    public Income(IncomeRequest incomeRequest, User user, IncomeSource incomeSource) {
        this.income = incomeRequest.getIncome();
        this.currency = incomeRequest.getCurrency();
        this.date = incomeRequest.getDate();
        this.description = incomeRequest.getDescription();
        this.user = user;
        this.incomeSource = incomeSource;
    }

    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public IncomeSource getIncomeSource() {
		return incomeSource;
	}


	public void setIncomeSource(IncomeSource incomeSource) {
		this.incomeSource = incomeSource;
	}


	public Double getIncome() {
		return income;
	}


	public void setIncome(Double income) {
		this.income = income;
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


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
    

}
