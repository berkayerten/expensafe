package com.techthrivecatalyst.expensafe.expense;

import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExpenseService {

    Page<Expense> list(String expenseCategoryId, Pageable pageable);

    Expense save(Expense expense);

    BigDecimal getTotalAmountSpentInCategory(UUID expenseCategoryId);
}
