package com.techthrivecatalyst.expensafe.expense;

public record MonthlyEstimates(
        double naiveEstimate,
        double weightedEstimate,
        double weightedWithHistoricalAdjustment
) {
}