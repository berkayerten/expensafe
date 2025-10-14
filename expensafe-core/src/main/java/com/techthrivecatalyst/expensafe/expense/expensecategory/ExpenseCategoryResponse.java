package com.techthrivecatalyst.expensafe.expense.expensecategory;

public class ExpenseCategoryResponse {
    private String id;
    private String name;
    private double budget;
    private boolean isFavorite;
    private double amountSpent;

    public ExpenseCategoryResponse(String id, String name, double budget, boolean isFavorite, double amountSpent) {
        this.id = id;
        this.name = name;
        this.budget = budget;
        this.isFavorite = isFavorite;
        this.amountSpent = amountSpent;
    }

    public ExpenseCategoryResponse() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBudget() {
        return budget;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public double getAmountSpent() {
        return amountSpent;
    }
}
