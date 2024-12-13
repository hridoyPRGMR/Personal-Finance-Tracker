package com.web_app.personal_finance.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web_app.personal_finance.model.Debt;
import com.web_app.personal_finance.service.DebtService;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

	private final DebtService debtService;

	public NotificationController(DebtService debtService) {
		this.debtService = debtService;
	}

	private static final Map<Integer, String> DAYS = Map.of(
		1, "Sunday", 
		2, "Monday", 
		3, "Tuesday", 
		4, "Wednesday", 
		5, "Thursday", 
		6, "Friday", 
		7, "Saturday");

	private static final Map<Integer, String> MONTHS = Map.ofEntries(
		Map.entry(1, "January"), 
		Map.entry(2, "February"),
		Map.entry(3, "March"), 
		Map.entry(4, "April"), 
		Map.entry(5, "May"), 
		Map.entry(6, "June"),
		Map.entry(7, "July"), 
		Map.entry(8, "August"), 
		Map.entry(9, "September"), 
		Map.entry(10, "October"),
		Map.entry(11, "November"), 
		Map.entry(12, "December")
	);

	@GetMapping
	public ResponseEntity<List<Notification>> getNotification(@AuthenticationPrincipal Jwt jwt) {

		Long userId = Long.parseLong(jwt.getClaim("userId"));
		List<Debt> debts = debtService.getAllDebtsByUserId(userId);

		LocalDate currentDate = LocalDate.now();
		String currentDayName = currentDate.getDayOfWeek().name();
		int currentDayOfMonth = currentDate.getDayOfMonth();
		String currentMonthName = currentDate.getMonth().name();
			
		
		List<Notification> notifications = debts.stream()
				.filter(debt -> matchesCondition(debt, currentDayName, currentDayOfMonth, currentMonthName))
				.map(debt -> {
					String message = switch (debt.getInstallmentType()) {
						case 1 -> "Installment must be paid on "+currentDayName;
						case 2 -> "Installment must be paid on "+ currentDayOfMonth;
						case 3 -> "Installments due this month.(" + currentMonthName + ")" ;
						default -> "Unknown installment type.";
					};
					return new Notification(message);
				})
				.collect(Collectors.toList());

		return ResponseEntity.ok(notifications);
	}

	private boolean matchesCondition(Debt debt, String dayName, int dayOfMonth, String monthName) {
		return switch (debt.getInstallmentType()) {
			case 1 -> checkDay(dayName, debt.getDay());
			case 2 -> checkDate(dayOfMonth, debt.getDate());
			case 3 -> checkMonth(monthName, debt.getMonth());
			default -> false;
		};
	}

	private boolean checkDay(String dayName, int day) {
		return DAYS.getOrDefault(day, "").equalsIgnoreCase(dayName);
	}

	private boolean checkDate(int dayOfMonth, int date) {
		return dayOfMonth == date;
	}

	private boolean checkMonth(String monthName, int month) {
		return MONTHS.getOrDefault(month, "").equalsIgnoreCase(monthName);
	}

	public record Notification(String message) {
	}
}
