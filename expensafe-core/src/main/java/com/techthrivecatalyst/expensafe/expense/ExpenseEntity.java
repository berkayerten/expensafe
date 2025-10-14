package com.techthrivecatalyst.expensafe.expense;

import com.techthrivecatalyst.expensafe.expense.expensecategory.ExpenseCategoryEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import static java.util.UUID.randomUUID;

@Entity
@Table(name = "expense")
public class ExpenseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String store;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "expense_category_id", nullable = false)
    private ExpenseCategoryEntity expenseCategoryEntity;

    private ExpenseEntity(LocalDate date, String store, BigDecimal price, ExpenseCategoryEntity expenseCategoryEntity) {
        this.date = date;
        this.store = store;
        this.price = price;
        this.expenseCategoryEntity = expenseCategoryEntity;
    }

    public ExpenseEntity() {
    }

    public static ExpenseEntity create(LocalDate date, String store, BigDecimal price,
            ExpenseCategoryEntity expenseCategoryEntity) {
        var expenseEntity = new ExpenseEntity(date, store, price, expenseCategoryEntity);
        expenseEntity.id = randomUUID().toString();
        return expenseEntity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }


    public String getStore() {
        return store;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getExpenseCategoryId() {
        return expenseCategoryEntity.getId();
    }
}