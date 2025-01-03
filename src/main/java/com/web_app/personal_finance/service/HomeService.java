package com.web_app.personal_finance.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web_app.personal_finance.helper.DateFilter;
import com.web_app.personal_finance.model.Expense;
import com.web_app.personal_finance.model.ExpenseItem;
import com.web_app.personal_finance.model.Income;

@Service
public class HomeService {

	private IncomeService incomeService;
	private CurrencyService currencyService;
	private ExpenseService expenseService;
	
	private HomeService(IncomeService incomeService, CurrencyService currencyService,ExpenseService expenseService) {
		this.incomeService = incomeService;
		this.currencyService = currencyService;
		this.expenseService = expenseService;
	}

	public Double []getIncomeRxpense(long userId, int filterBy, String currency) {
		
		List<Income> incomes = incomeService.getAllIncome(userId);
		List<Expense> expenses = expenseService.getAllExpenses(userId);
		
		Map<String, Double> currencyRates = currencyService.getExchangeRate("USD").entrySet()
			        .stream()
			        .collect(Collectors.toMap(
			            Map.Entry::getKey,
			            entry -> ((Number) entry.getValue()).doubleValue()
			        ));
		
		
		Predicate<LocalDate> dateFilter;

		switch (filterBy) {
			case 1:
				dateFilter = DateFilter.currentWeek();
			break;
			case 2:
				dateFilter = DateFilter.currentMonth();
				break;
			case 3:
				dateFilter = DateFilter.currentYear();
				break;
			default:
				throw new IllegalArgumentException("Invalid filter type");
		}

		if (!currencyRates.containsKey(currency)) {
			throw new IllegalArgumentException("Invalid or unsupported currency: " + currency);
		}

		double totalIncomeInUSD = incomes.stream()
										.filter(income -> dateFilter.test(income.getDate())) 
										.mapToDouble(income -> income.getIncome() / currencyRates.get(income.getCurrency()))
										.sum();
		
		double totalIncomeInTargetCurrency = totalIncomeInUSD * currencyRates.get(currency);
			
		double totalExpenseInUSD = expenses.stream()
											.filter(expense -> dateFilter.test(expense.getDate()))
											.flatMap(expense -> expense.getExpenses().stream())
											.mapToDouble(expenseItem -> expenseItem.getAmount() / currencyRates.get(expenseItem.getExpense().getCurrency()) )
											.sum();
		
		double totalExpenseInTargetCurrency = totalExpenseInUSD * currencyRates.get(currency);
		
		
		
		Double []incomeExpense = {totalIncomeInTargetCurrency,totalExpenseInTargetCurrency};
		
		return incomeExpense;
	}

}
