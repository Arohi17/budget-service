package com.prp.budgetservice.entity;


import com.arangodb.entity.Id;
import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.Field;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "month_budgets")
public class MonthBudget {

    @Id
    private String id;

    @Field("user_id")
    private String userId;

    @Field("month")
    private Integer month;

    @Field("year")
    private Integer year;

    @Field("budget_amount")
    private Double BudgetAmount;

    @Field("spent_amount")
    private Double spentAmount;

    @Field("remaining_amount")
    private Double remainingAmount;

    @Field("locked")
    private Boolean Locked;

    @Field("last_updated_at")
    private LocalDateTime lastUpdatedAt;

}
