package com.techthrivecatalyst.expensafe.expense;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class ExpenseRequest {

    private UUID id;
    private Instant date;
    private String store;
    private double amount;
    private UUID expenseCategoryId;

    public UUID getId() {
        return id;
    }

    public Instant getDate() {
        return date;
    }

    public String getStore() {
        return store;
    }

    public double getAmount() {
        return amount;
    }

    public UUID getExpenseCategoryId() {
        return expenseCategoryId;
    }
}
