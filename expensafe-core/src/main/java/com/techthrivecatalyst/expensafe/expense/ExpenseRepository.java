package com.techthrivecatalyst.expensafe.expense;

import java.math.BigDecimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, String> {

    @Query("SELECT e " +
            "FROM ExpenseEntity e " +
            "WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month AND e.expenseCategoryEntity.id = :categoryId " +
            "ORDER BY e.date DESC")
    Page<ExpenseEntity> findByYearAndMonthOrderByDateDesc(
            @Param("year") int year,
            @Param("month") int month,
            @Param("categoryId") String categoryId,
            Pageable pageable
    );

    @Query("SELECT SUM(e.price) AS total_amount " +
            "FROM ExpenseEntity e " +
            "WHERE e.expenseCategoryEntity.id = :expenseCategoryId")
    BigDecimal sumTotalAmountByExpenseCategoryEntity_Id(String expenseCategoryId);
}
