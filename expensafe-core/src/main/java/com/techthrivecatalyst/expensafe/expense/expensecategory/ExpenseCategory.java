package com.techthrivecatalyst.expensafe.expense.expensecategory;

import java.math.BigDecimal;
import java.util.UUID;

public record ExpenseCategory(UUID id, String name, BigDecimal budget, boolean isFavorite) {
}
