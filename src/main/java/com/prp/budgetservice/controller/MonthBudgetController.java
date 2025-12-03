package com.prp.budgetservice.controller;

import com.prp.budgetservice.dto.month.BudgetReminderStatusDTO;
import com.prp.budgetservice.dto.month.MonthBudgetRequestDTO;
import com.prp.budgetservice.dto.month.MonthBudgetResponseDTO;
import com.prp.budgetservice.dto.reward.RewardResponseDTO;
import com.prp.budgetservice.service.MonthBudgetService;
import com.prp.budgetservice.service.RewardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class MonthBudgetController {

    private final MonthBudgetService monthBudgetService;
    private final RewardService rewardService;

    // Create new budget
    @PostMapping("/user/{userId}")
    public ResponseEntity<MonthBudgetResponseDTO> createBudget(
            @PathVariable String userId,
            @RequestBody @Valid MonthBudgetRequestDTO requestDTO) {

        MonthBudgetResponseDTO response = monthBudgetService.createMonthBudget(userId, requestDTO);
        return ResponseEntity.ok(response);
    }

    // Get all budgets
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MonthBudgetResponseDTO>> getAllBudgets(
            @PathVariable String userId) {

        List<MonthBudgetResponseDTO> response = monthBudgetService.getAllBudgets(userId);
        return ResponseEntity.ok(response);
    }

    // Get budget for a specific month/year
    @GetMapping("/user/{userId}/{month}/{year}")
    public ResponseEntity<MonthBudgetResponseDTO> getBudget(
            @PathVariable String userId,
            @PathVariable int month,
            @PathVariable int year) {

        MonthBudgetResponseDTO response = monthBudgetService.getMonthBudget(userId, month, year);
        return ResponseEntity.ok(response);
    }

    // Update spent amount
    @PutMapping("/user/{userId}/{month}/{year}/spent")
    public ResponseEntity<MonthBudgetResponseDTO> updateSpent(
            @PathVariable String userId,
            @PathVariable int month,
            @PathVariable int year,
            @RequestParam double spentAmount) {

        MonthBudgetResponseDTO response = monthBudgetService.updateSpentAmount(userId, month, year, spentAmount);
        return ResponseEntity.ok(response);
    }

    // Lock a budget
    @PutMapping("/user/{userId}/{month}/{year}/lock")
    public ResponseEntity<Void> lockBudget(
            @PathVariable String userId,
            @PathVariable int month,
            @PathVariable int year) {

        monthBudgetService.lockBudget(userId, month, year);
        return ResponseEntity.ok().build();
    }

    // Reward
    @PostMapping("/user/{userId}/{month}/{year}/reward")
    public ResponseEntity<RewardResponseDTO> giveReward(
            @PathVariable String userId,
            @PathVariable int month,
            @PathVariable int year) {

        RewardResponseDTO rewardResponse = rewardService.calculateMonthlyReward(userId, month, year);
        return ResponseEntity.ok(rewardResponse);
    }

    // Reminder
    @GetMapping("/user/{userId}/reminder")
    public ResponseEntity<BudgetReminderStatusDTO> getBudgetReminder(@PathVariable String userId) {
        BudgetReminderStatusDTO reminderStatus = rewardService.getReminderStatus(userId);
        return ResponseEntity.ok(reminderStatus);
    }
}
