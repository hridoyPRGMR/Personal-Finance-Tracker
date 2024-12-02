package com.web_app.personal_finance.helper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.function.Predicate;

public class DateFilter {

    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate START_OF_MONTH = TODAY.withDayOfMonth(1);
    private static final LocalDate END_OF_MONTH = TODAY.withDayOfMonth(TODAY.lengthOfMonth());
    private static final int CURRENT_YEAR = TODAY.getYear();
    private static final LocalDate START_OF_YEAR = TODAY.withDayOfYear(1);
    private static final LocalDate END_OF_YEAR = TODAY.withDayOfYear(TODAY.lengthOfYear());

    public static Predicate<LocalDate> currentWeek() {
        LocalDate startOfWeek = TODAY.with(DayOfWeek.SUNDAY);
        LocalDate endOfWeek = TODAY.with(DayOfWeek.SATURDAY);

        return date -> date.isAfter(startOfWeek.minusDays(1)) && date.isBefore(endOfWeek.plusDays(1))
                && date.getMonth() == TODAY.getMonth()
                && date.getYear() == CURRENT_YEAR;
    }

    public static Predicate<LocalDate> currentMonth() {
        return date -> !date.isBefore(START_OF_MONTH) && !date.isAfter(END_OF_MONTH)
                && date.getYear() == CURRENT_YEAR;
    }

    public static Predicate<LocalDate> currentYear() {
        return date -> !date.isBefore(START_OF_YEAR) && !date.isAfter(END_OF_YEAR);
    }
}
