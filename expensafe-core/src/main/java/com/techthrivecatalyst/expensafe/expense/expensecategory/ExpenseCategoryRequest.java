package com.techthrivecatalyst.expensafe.expense.expensecategory;

import java.math.BigDecimal;

public class ExpenseCategoryRequest {
    private String name;
    private double budget;
    private boolean favorite;

    public ExpenseCategoryRequest() {
    }

    public ExpenseCategoryRequest(String name, double budget, boolean favorite) {
        this.name = name;
        this.budget = budget;
        this.favorite = favorite;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBudget() {
        return BigDecimal.valueOf(budget);
    }

    public boolean isFavorite() {
        return favorite;
    }


}
