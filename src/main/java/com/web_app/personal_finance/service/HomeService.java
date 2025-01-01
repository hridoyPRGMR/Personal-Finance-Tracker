package com.web_app.personal_finance.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web_app.personal_finance.helper.DateFilter;
import com.web_app.personal_finance.model.Income;

@Service
public class HomeService {

	private IncomeService incomeService;
	private CurrencyService currencyService;

	private HomeService(IncomeService incomeService, CurrencyService currencyService) {
		this.incomeService = incomeService;
		this.currencyService = currencyService;
	}

	public Double totalIncome(long userId, int filterBy, String currency) {
		
		List<Income> incomes = incomeService.getAllIncome(userId);
		
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

		return totalIncomeInTargetCurrency;
	}

}
