package com.prp.budgetservice.service.impl;

import com.prp.budgetservice.dto.month.MonthBudgetRequestDTO;
import com.prp.budgetservice.dto.month.MonthBudgetResponseDTO;
import com.prp.budgetservice.entity.MonthBudget;
import com.prp.budgetservice.repository.MonthBudgetRepository;
import com.prp.budgetservice.service.MonthBudgetService;
import com.prp.budgetservice.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MonthBudgetServiceImpl implements MonthBudgetService {

    private final MonthBudgetRepository monthBudgetRepository;
    private final ModelMapper modelMapper;
    private final RewardService rewardService;

    @Override
    public MonthBudgetResponseDTO createMonthBudget(String userId, MonthBudgetRequestDTO requestDTO) {

        // Map request → entity
        MonthBudget budget = modelMapper.map(requestDTO, MonthBudget.class);
        budget.setUserId(userId);
        budget.setSpentAmount(0.0);
        budget.setLocked(false);
        budget.setLastUpdatedAt(null);
        budget.setRemainingAmount(budget.getBudgetAmount());

        // Save to DB
        monthBudgetRepository.save(budget);

        // Reward coins for setting budget
        rewardService.handleCoinsForBudgetCreation(
                userId,
                requestDTO.getMonth(),
                requestDTO.getYear()
        );

        // Map entity → response DTO
        MonthBudgetResponseDTO dto = modelMapper.map(budget, MonthBudgetResponseDTO.class);
        dto.setRemainingAmount(budget.getBudgetAmount() - budget.getSpentAmount());
        return dto;
    }

    @Override
    public MonthBudgetResponseDTO getMonthBudget(String userId, Integer month, Integer year) {
        MonthBudget budget = monthBudgetRepository
                .findByUserIdAndMonthAndYear(userId, month, year)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        MonthBudgetResponseDTO dto = modelMapper.map(budget, MonthBudgetResponseDTO.class);
        dto.setRemainingAmount(budget.getBudgetAmount() - budget.getSpentAmount());
        return dto;
    }

    @Override
    public List<MonthBudgetResponseDTO> getAllBudgets(String userId) {
        return monthBudgetRepository.findByUserId(userId).stream()
                .map(budget -> {
                    MonthBudgetResponseDTO dto = modelMapper.map(budget, MonthBudgetResponseDTO.class);
                    dto.setRemainingAmount(budget.getBudgetAmount() - budget.getSpentAmount());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public MonthBudgetResponseDTO updateSpentAmount(String userId, Integer month, Integer year, Double spentAmount) {

        MonthBudget budget = monthBudgetRepository
                .findByUserIdAndMonthAndYear(userId, month, year)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        // Block updates if locked
        if (Boolean.TRUE.equals(budget.getLocked())) {
            throw new RuntimeException("Budget is locked and cannot be updated");
        }

        // Allow updating only once per 7 days
        LocalDateTime now = LocalDateTime.now();
        if (budget.getLastUpdatedAt() != null) {
            Duration duration = Duration.between(budget.getLastUpdatedAt(), now);
            if (duration.toDays() < 7) {
                throw new RuntimeException("Budget can only be updated once per week");
            }
        }

        // Update spent amount
        budget.setSpentAmount(budget.getSpentAmount() + spentAmount);
        budget.setLastUpdatedAt(now);
        budget.setRemainingAmount(budget.getBudgetAmount() - budget.getSpentAmount());

        monthBudgetRepository.save(budget);

        // Map to DTO
        MonthBudgetResponseDTO dto = modelMapper.map(budget, MonthBudgetResponseDTO.class);
        dto.setRemainingAmount(budget.getRemainingAmount());
        return dto;
    }

    @Override
    public void lockBudget(String userId, Integer month, Integer year) {

        MonthBudget budget = monthBudgetRepository
                .findByUserIdAndMonthAndYear(userId, month, year)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        budget.setLocked(true);
        monthBudgetRepository.save(budget);
    }
}

