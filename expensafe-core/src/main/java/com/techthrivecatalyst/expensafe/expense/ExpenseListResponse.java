package com.techthrivecatalyst.expensafe.expense;

import org.springframework.data.domain.Page;

public record ExpenseListResponse(Page<Expense> expenses, MonthlyEstimates estimates) {
}