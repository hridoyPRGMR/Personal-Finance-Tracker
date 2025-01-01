package com.web_app.personal_finance.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web_app.personal_finance.helper.DateFilter;
import com.web_app.personal_finance.model.Debt;
import com.web_app.personal_finance.model.Expense;
import com.web_app.personal_finance.model.ExpenseItem;
import com.web_app.personal_finance.model.Income;
import com.web_app.personal_finance.model.User;
import com.web_app.personal_finance.security.JwtTokenUtil;
import com.web_app.personal_finance.service.DebtService;
import com.web_app.personal_finance.service.ExpenseService;
import com.web_app.personal_finance.service.HomeService;
import com.web_app.personal_finance.service.IncomeService;

@RestController
@RequestMapping("/api/home")
public class HomeController {
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private IncomeService incomeService;
	@Autowired
	private ExpenseService expenseService;
	@Autowired
	private DebtService debtService;
	
	@Autowired
	private HomeService homeService;
	
	// filter by 1=current week,2==current month,3=current year
	@GetMapping("/summary")
	public Summary getSummary(@AuthenticationPrincipal Jwt jwt,@RequestParam(defaultValue="BDT")String currency,@RequestParam(defaultValue="1")int filterby) {
		
		User user = jwtTokenUtil.getUser(jwt);
		
		List<Expense> expenses = expenseService.getAllExpenses(user.getId());
		List<Debt> debts = debtService.getAllDebtsByUserId(user.getId());
		
		Double totalIncome= homeService.totalIncome(user.getId(), filterby, currency);
		
		
		
//		System.out.println(totalIncome);
		
		Double totalExpense = 0.00;
		Double totalDebtsWithoutInterest = 0.00;
		Double totalDebtsWithInterest = 0.00;
		Double payOff = 0.00;
		
		Predicate<LocalDate> dateFilter;
		
		switch(filterby) {
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

		totalExpense = expenses.stream()
								.filter(expense -> dateFilter.test(expense.getDate()))
								.flatMap(expense -> expense.getExpenses().stream())
								.mapToDouble(ExpenseItem::getAmount)
								.sum();
		
		for(Debt debt:debts) {
			totalDebtsWithoutInterest += debt.getOutstandingBalance();
			totalDebtsWithInterest += debt.getOutstandingBalance() + interest(debt);
			
			int installmentType = debt.getInstallmentType();
			
			if(filterby==1 && installmentType == 1) {
				payOff += debt.getMinimumPayment();
			}
			else if(filterby == 2 && (installmentType == 2 || installmentType == 1)) {
				payOff += debt.getMinimumPayment();
			}
			else if(filterby == 3) {
				payOff += debt.getMinimumPayment();
			}
		}
		
		
		return new Summary(totalIncome,totalExpense,totalDebtsWithoutInterest,totalDebtsWithInterest,payOff);
	}
	
	//Simple Interest is calculated using the following formula: SI = P × R × T, where P = Principal, R = Rate of Interest, and T = Time period.
	public Double interest(Debt debt) {
		
		Double interestRate = debt.getInterestRate();
		Double balance = debt.getOutstandingBalance();
		int time = debt.getLoanTenure();
		
		return ((balance * interestRate)*(time/12)) /100 ;
		
	}

	private static record Summary(Double totalIncome, Double totalExpense,Double totalDebtsWithoutInterest,Double totalDebtsWithInterest,double payOff) {
	}
}
