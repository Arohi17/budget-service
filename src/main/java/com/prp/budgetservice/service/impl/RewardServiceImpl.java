package com.prp.budgetservice.service.impl;

import com.prp.budgetservice.dto.month.BudgetReminderStatusDTO;
import com.prp.budgetservice.dto.notification.NotificationRequestDTO;
import com.prp.budgetservice.dto.reward.RewardResponseDTO;
import com.prp.budgetservice.entity.MonthBudget;
import com.prp.budgetservice.entity.MonthlyCoin;
import com.prp.budgetservice.repository.MonthBudgetRepository;
import com.prp.budgetservice.repository.MonthlyCoinRepository;
import com.prp.budgetservice.service.NotificationService;
import com.prp.budgetservice.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {

    private final MonthBudgetRepository monthBudgetRepository;
    private final MonthlyCoinRepository monthlyCoinRepository;
    private final NotificationService notificationService;

    private static final int BUDGET_CREATION_REWARD = 50;

    // Award coins when user sets a budget
    @Override
    public MonthlyCoin handleCoinsForBudgetCreation(String userId, int month, int year) {

        // If already awarded this month --- return existing
        MonthlyCoin existing = monthlyCoinRepository.findByUserIdAndMonthAndYear(userId, month, year);
        if (existing != null) {
            return existing;
        }

        // Find last month's coin record
        MonthlyCoin last = monthlyCoinRepository.findLatestForUser(userId);
        int previousCoins = (last != null) ? last.getTotalCoins() : 0;

        // New coin entry
        MonthlyCoin record = new MonthlyCoin();
        record.setUserId(userId);
        record.setMonth(month);
        record.setYear(year);
        record.setCoinsCarried(previousCoins);
        record.setCoinsEarned(BUDGET_CREATION_REWARD);
        record.setTotalCoins(previousCoins + BUDGET_CREATION_REWARD);

        monthlyCoinRepository.save(record);

        // Notify user
        NotificationRequestDTO requestDTO = new NotificationRequestDTO();
        requestDTO.setUserId(userId);
        requestDTO.setTitle("You earned " + BUDGET_CREATION_REWARD + " coins!");
        requestDTO.setMessage("Thanks for setting your budget for " + month + "/" + year);

        notificationService.createNotification(requestDTO);

        return record;
    }

    // Calculate reward
    @Override
    public RewardResponseDTO calculateMonthlyReward(String userId, int month, int year) {
        MonthlyCoin record = handleCoinsForBudgetCreation(userId, month, year);

        RewardResponseDTO dto = new RewardResponseDTO();
        dto.setUserId(record.getUserId());
        dto.setMonth(record.getMonth());
        dto.setYear(record.getYear());
        dto.setCoinsCarried(record.getCoinsCarried());
        dto.setCoinsEarned(record.getCoinsEarned());
        dto.setTotalCoins(record.getTotalCoins());

        return dto;
    }

    // Reminder logic: show reminder for 3 days then auto carry-forward
    @Override
    public BudgetReminderStatusDTO getReminderStatus(String userId) {

        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        int year = today.getYear();

        BudgetReminderStatusDTO dto = new BudgetReminderStatusDTO();

        Optional<MonthBudget> existing = monthBudgetRepository.findByUserIdAndMonthAndYear(userId, month, year);

        if (existing.isPresent()) {
            dto.setShowReminder(false);
            dto.setCarriedForward(false);
            return dto;
        }

        if (today.getDayOfMonth() <= 3) {
            dto.setShowReminder(true);
            dto.setCarriedForward(false);
            return dto;
        }

        carryForwardLastMonthBudget(userId, month, year);

        dto.setShowReminder(false);
        dto.setCarriedForward(true);
        return dto;
    }

    // Create a new MonthBudget from last month's details
    private void carryForwardLastMonthBudget(String userId, int month, int year) {

        MonthBudget last = monthBudgetRepository.findLastMonth(userId);
        if (last == null) return;

        Optional<MonthBudget> existing = monthBudgetRepository.findByUserIdAndMonthAndYear(userId, month, year);
        if (existing.isPresent()) return;

        MonthBudget newBudget = new MonthBudget();
        newBudget.setUserId(userId);
        newBudget.setMonth(month);
        newBudget.setYear(year);
        newBudget.setBudgetAmount(last.getBudgetAmount());
        newBudget.setSpentAmount(0.0);
        newBudget.setRemainingAmount(last.getBudgetAmount());
        newBudget.setLocked(false);
        newBudget.setLastUpdatedAt(null);

        monthBudgetRepository.save(newBudget);
    }
}
