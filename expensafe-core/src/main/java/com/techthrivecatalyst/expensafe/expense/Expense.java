package com.techthrivecatalyst.expensafe.expense;

import java.time.LocalDate;
import java.util.UUID;

public record Expense(UUID id, LocalDate date, String store, double amount, UUID expenseCategoryId) {
}
