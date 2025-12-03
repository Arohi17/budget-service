package com.prp.budgetservice.dto.month;

import lombok.Data;

@Data
public class BudgetReminderStatusDTO {
    private boolean showReminder;
    private boolean carriedForward;
}
