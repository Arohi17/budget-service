package com.prp.budgetservice.dto.reward;

import lombok.Data;

@Data
public class RewardResponseDTO {
    private String userId;
    private Integer month;
    private Integer year;
    private Integer coinsCarried;
    private Integer coinsEarned;
    private Integer totalCoins;
}
