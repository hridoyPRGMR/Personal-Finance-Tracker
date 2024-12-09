package com.web_app.personal_finance.controller;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web_app.personal_finance.model.Expense;
import com.web_app.personal_finance.model.ExpenseItem;
import com.web_app.personal_finance.model.Income;
import com.web_app.personal_finance.service.ExpenseService;
import com.web_app.personal_finance.service.IncomeService;

@RestController
@RequestMapping("api/report")
public class ReportController {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/income")
    public ResponseEntity<IncomeData> getIncomes(@AuthenticationPrincipal Jwt jwt,
            @RequestParam(defaultValue = "2024") Integer year,
            @RequestParam(required = false) Long incomeSource) {

        Long userId = Long.parseLong(jwt.getClaim("userId"));

        List<Income> incomes = incomeService.getAllFilteredIncome(userId, year, incomeSource);

        Map<Month, List<Income>> incomesByMonth = incomes.stream()
                .collect(Collectors.groupingBy(income -> income.getDate().getMonth()));

        List<Double> incomeData = new ArrayList<>();
        List<Month> monthData = new ArrayList<>();

        for (Map.Entry<Month, List<Income>> entry : incomesByMonth.entrySet()) {

            Double monthlyIncome = entry.getValue().stream()
                    .mapToDouble(Income::getIncome)
                    .sum();

            incomeData.add(monthlyIncome);
            monthData.add(entry.getKey());
        }

        return ResponseEntity.ok(new IncomeData(monthData, incomeData));
    }

    @GetMapping("/expense")
    public ResponseEntity<?> getExpenses(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {

        Long userId = Long.parseLong(jwt.getClaim("userId"));
        List<Expense> expenses = expenseService.getAllFilteredExpenses(userId, month, year);

        // If month is provided, calculate daily expenses for the month
        if (month != null) {
            Map<Integer, Double> dailyExpenses = calculateDailyExpenses(expenses);
            return ResponseEntity.ok(dailyExpenses);
        }

        // Otherwise, calculate monthly expenses for the year
        Map<Integer, Double> monthlyExpenses = calculateMonthlyExpenses(expenses);
        return ResponseEntity.ok(monthlyExpenses);
    }

    //method to calculate daily expenses
    private Map<Integer, Double> calculateDailyExpenses(List<Expense> expenses) {
        return expenses.stream()
                .collect(Collectors.groupingBy(expense -> expense.getDate().getDayOfMonth(),
                        Collectors.summingDouble(expense -> expense.getExpenses().stream()
                        .mapToDouble(ExpenseItem::getAmount)
                        .sum())));
    }

    //method to calculate monthly expenses
    private Map<Integer, Double> calculateMonthlyExpenses(List<Expense> expenses) {
        return expenses.stream()
                .collect(Collectors.groupingBy(expense -> expense.getDate().getMonthValue(),
                        Collectors.summingDouble(expense -> expense.getExpenses().stream()
                        .mapToDouble(ExpenseItem::getAmount)
                        .sum())));
    }

	public record IncomeData(List<Month> months,List<Double> incomes){}
}
