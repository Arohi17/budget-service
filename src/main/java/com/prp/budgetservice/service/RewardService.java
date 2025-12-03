package com.prp.budgetservice.service;

import com.prp.budgetservice.dto.month.BudgetReminderStatusDTO;
import com.prp.budgetservice.dto.reward.RewardResponseDTO;
import com.prp.budgetservice.entity.MonthlyCoin;

public interface RewardService {

    MonthlyCoin handleCoinsForBudgetCreation(String userId, int month, int year);

    BudgetReminderStatusDTO getReminderStatus(String userId);

    RewardResponseDTO calculateMonthlyReward(String userId, int month, int year);

}
