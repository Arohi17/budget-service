package com.prp.budgetservice.dto.month;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MonthBudgetRequestDTO {

    @NotNull(message = "Month is required")
    @Min(value = 1, message = "Month must be between 1-12")
    @Max(value = 12, message = "Month must be between 1-12")
    private Integer month;

    @NotNull(message = "Year is required")
    private Integer year;

    @NotNull(message = "Budget amount is required")
    @Min(value = 1, message = "Budget must be at least ₹1")
    private Double budgetAmount;
}
