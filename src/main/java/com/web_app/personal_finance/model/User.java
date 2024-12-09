package com.web_app.personal_finance.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = true)
    @Size(min=3,max=50)
    private String username;
    
    @Column(unique=true,nullable = false)
    @Email(message="Invalid email")
    private String email;
    
    @Column(unique=false,nullable=true)
    @Size(min=3,max=50)
    private String name;
    
    
    @Column(nullable = false)
    @Size(min=6,max=50)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private boolean enabled;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime created_at;
	 
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    
    @PrePersist
    protected void onCreate() {
    	created_at = LocalDateTime.now();
    }
    
    
    
    @PreUpdate
    protected void onUpdate() {
    	updated_at = LocalDateTime.now();
    }
    

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<UserRole> roles;
    
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval=true)
    private List<IncomeSource>incomeSources;
    
    //default constructor
    public User() {
	}
	
	public User(
			@Size(min = 3, max = 50) String username, 
			String email,
			@Size(min = 3, max = 50) String name,
			@Size(min = 6, max = 50) String password
	){
		super();
		this.username = username;
		this.email = email;
		this.name = name;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	

	public List<IncomeSource> getIncomeSources() {
		return incomeSources;
	}

	public void setIncomeSources(List<IncomeSource> incomeSources) {
		this.incomeSources = incomeSources;
	}
	

}
