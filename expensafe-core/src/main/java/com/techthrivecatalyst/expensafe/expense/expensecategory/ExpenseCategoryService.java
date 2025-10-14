package com.techthrivecatalyst.expensafe.expense.expensecategory;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExpenseCategoryService {

    Page<ExpenseCategory> list(Pageable pageable, UUID userId);

    ExpenseCategory save(ExpenseCategory expense, UUID userId);
}
