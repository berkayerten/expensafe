package com.techthrivecatalyst.expensafe.expense.expensecategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

import static java.util.UUID.randomUUID;

@Entity
@Table(name = "expense_category")
public class ExpenseCategoryEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column
    private BigDecimal budget;

    public ExpenseCategoryEntity() {
    }

    private ExpenseCategoryEntity(String name, BigDecimal budget) {
        this.name = name;
        this.budget = budget;
    }

    public static ExpenseCategoryEntity create(String name, BigDecimal budgetLimit) {
        var expenseCategoryEntity = new ExpenseCategoryEntity(name, budgetLimit);
        expenseCategoryEntity.id = randomUUID().toString();
        return expenseCategoryEntity;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public String getId() {
        return id;
    }
}
