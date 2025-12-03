package com.prp.budgetservice.dto.month;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MonthBudgetResponseDTO {

    private String id;
    private Integer month;
    private Integer year;
    private Double BudgetAmount;
    private Double spentAmount;
    private Double remainingAmount;
    private boolean locked;
    private LocalDateTime lastUpdatedAt;
}
