package com.web_app.personal_finance.projection;

import java.time.LocalDate;

public interface IncomeProjection {

	Long getId();

	Double getIncome();

	LocalDate getDate();

	String getDescription();

	IncomeSourceProjection getIncomeSource();

	UserProjection getUser();

	interface IncomeSourceProjection {
		Long getId();
		String getIncomeSource();
	}

	interface UserProjection {
		Long getId();
		String getUsername();
	}
}
