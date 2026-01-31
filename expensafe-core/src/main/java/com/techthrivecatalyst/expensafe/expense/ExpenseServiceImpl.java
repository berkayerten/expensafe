package com.techthrivecatalyst.expensafe.expense;

import com.techthrivecatalyst.expensafe.expense.expensecategory.ExpenseCategoryRepository;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.UUID.fromString;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final EstimateCalculator estimateCalculator;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository,
            ExpenseCategoryRepository expenseCategoryRepository,
            EstimateCalculator estimateCalculator) {
        this.expenseRepository = expenseRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.estimateCalculator = estimateCalculator;
    }

    public Page<Expense> list(String expenseCategoryId, Pageable pageable) {
        YearMonth current = YearMonth.now();
        return expenseRepository
                .findByYearAndMonthOrderByDateDesc(
                        current.getYear(),
                        current.getMonthValue(),
                        expenseCategoryId,
                        pageable
                )
                .map(this::toDto);
    }

    @Transactional
    public Expense save(Expense expense) {
        if (expense.expenseCategoryId() == null) {
            throw new IllegalArgumentException("Expense category cannot be empty");
        }
        ExpenseEntity entity = toEntity(expense);
        entity = expenseRepository.save(entity);
        return toDto(entity);
    }

    @Override
    public BigDecimal getTotalAmountSpentInCategory(UUID expenseCategoryId) {
        BigDecimal totalSpentInCategory = expenseRepository.sumTotalAmountByExpenseCategoryEntity_Id(
                expenseCategoryId.toString());
        return totalSpentInCategory != null ? totalSpentInCategory : new BigDecimal(0);
    }

    @Override
    public ExpenseListResponse listWithEstimates(String expenseCategoryId, Pageable pageable) {
        Page<Expense> expenses = list(expenseCategoryId, pageable);
        MonthlyEstimates estimates = estimateCalculator.calculateEstimates(expenseCategoryId);
        return new ExpenseListResponse(expenses, estimates);
    }

    private ExpenseEntity toEntity(Expense expense) {
        return expenseCategoryRepository.findById(expense.expenseCategoryId().toString())
                .map(expenseCategory ->
                        ExpenseEntity.create(expense.date(), expense.store(), BigDecimal.valueOf(expense.amount()),
                                expenseCategory)
                ).orElseThrow(() -> new IllegalArgumentException(
                        "Expense category with id " + expense.expenseCategoryId() + "  is not found"));
    }

    private Expense toDto(ExpenseEntity entity) {
        return new Expense(fromString(entity.getId()), entity.getDate(), entity.getStore(),
                entity.getPrice().doubleValue(), fromString(entity.getExpenseCategoryId()));
    }
}
