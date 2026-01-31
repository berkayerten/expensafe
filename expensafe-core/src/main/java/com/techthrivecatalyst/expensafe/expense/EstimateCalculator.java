package com.techthrivecatalyst.expensafe.expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import org.springframework.stereotype.Component;

@Component
public class EstimateCalculator {

    private final ExpenseRepository expenseRepository;

    public EstimateCalculator(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public MonthlyEstimates calculateEstimates(String categoryId) {
        LocalDate today = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(today);
        YearMonth previousMonth = currentMonth.minusMonths(1);

        int daysPassed = today.getDayOfMonth();
        int totalDaysInMonth = currentMonth.lengthOfMonth();

        // Get current month expenses
        BigDecimal currentMonthTotal = expenseRepository.sumByYearAndMonth(
                currentMonth.getYear(),
                currentMonth.getMonthValue(),
                categoryId
        );
        double currentExpenses = currentMonthTotal != null ? currentMonthTotal.doubleValue() : 0.0;

        // 1. Naive Estimate: currentExpenses / daysPassed × totalDays
        double naiveEstimate = daysPassed > 0
                ? (currentExpenses / daysPassed) * totalDaysInMonth
                : 0.0;

        // 2. Weighted Estimate: current_expenses + average_daily_last7 × (total_days - days_passed)
        LocalDate sevenDaysAgo = today.minusDays(6); // Last 7 days including today
        BigDecimal last7DaysTotal = expenseRepository.sumByDateRangeAndCategory(
                sevenDaysAgo,
                today,
                categoryId
        );
        double last7DaysExpenses = last7DaysTotal != null ? last7DaysTotal.doubleValue() : 0.0;
        double averageDailyLast7 = last7DaysExpenses / 7.0;
        int remainingDays = totalDaysInMonth - daysPassed;
        double weightedEstimate = currentExpenses + (averageDailyLast7 * remainingDays);

        // 3. Weighted with Historical Adjustment: 0.7 × current_projection + 0.3 × last_month's_total
        BigDecimal previousMonthTotal = expenseRepository.sumByYearAndMonth(
                previousMonth.getYear(),
                previousMonth.getMonthValue(),
                categoryId
        );
        double lastMonthExpenses = previousMonthTotal != null ? previousMonthTotal.doubleValue() : 0.0;
        double weightedWithHistorical = (0.7 * naiveEstimate) + (0.3 * lastMonthExpenses);

        return new MonthlyEstimates(naiveEstimate, weightedEstimate, weightedWithHistorical);
    }
}

