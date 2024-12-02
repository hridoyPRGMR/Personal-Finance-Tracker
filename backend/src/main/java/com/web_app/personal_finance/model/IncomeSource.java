package com.web_app.personal_finance.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="income_source")
public class IncomeSource {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message="income_type can not be null")
	private String income_type;
	
	@NotNull(message="income_source can not be null")
	private String income_source;
	
	@Column(name="description",nullable=true)
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "user_id",nullable = false)
	@JsonIgnore 
	//This will allow you to define the getUser() method, but it will prevent the User from being serialized when returning IncomeSource objects, thereby breaking the circular reference.
	//	@JsonIgnore if you don't need to serialize the User object when retrieving IncomeSource records.
	private User user;
	
	@Column(name="created_at", updatable = false)
	private LocalDateTime created_at;
	
	@Column(name="updated_at")
	private LocalDateTime updated_at;
	
	

	public IncomeSource() {
	}

	public IncomeSource(@NotNull(message = "income_type can not be null") String income_type,
			@NotNull(message = "income_source can not be null") String income_source, String description) {
		super();
		this.income_type = income_type;
		this.income_source = income_source;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIncome_type() {
		return income_type;
	}

	public void setIncome_type(String income_type) {
		this.income_type = income_type;
	}

	public String getIncome_source() {
		return income_source;
	}

	public void setIncome_source(String income_source) {
		this.income_source = income_source;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public LocalDateTime getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}


	@Override
	public String toString() {
		return "IncomeSource [id=" + id + ", income_type=" + income_type + ", income_source=" + income_source
				+ ", description=" + description + ", created_at=" + created_at + ", updated_at=" + updated_at + "]";
	}
	
	@PrePersist
    protected void onCreate() {
    	created_at = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
    	updated_at = LocalDateTime.now();
    }
	
	
}
