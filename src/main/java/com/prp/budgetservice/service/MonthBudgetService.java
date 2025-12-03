package com.prp.budgetservice.service;

import com.prp.budgetservice.dto.month.MonthBudgetRequestDTO;
import com.prp.budgetservice.dto.month.MonthBudgetResponseDTO;
import com.prp.budgetservice.entity.MonthBudget;

import java.util.List;

public interface MonthBudgetService {

    MonthBudgetResponseDTO createMonthBudget(String userId, MonthBudgetRequestDTO requestDTO);

    MonthBudgetResponseDTO getMonthBudget(String userId, Integer month, Integer year);

    List<MonthBudgetResponseDTO> getAllBudgets(String userId);

    MonthBudgetResponseDTO updateSpentAmount(String userId, Integer month, Integer year, Double spentAmount);

    void lockBudget(String userId, Integer month, Integer year);

}
