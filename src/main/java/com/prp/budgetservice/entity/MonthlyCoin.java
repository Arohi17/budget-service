package com.prp.budgetservice.entity;

import com.arangodb.entity.Id;
import com.arangodb.springframework.annotation.Document;
import lombok.Data;

@Data
@Document("monthly_coins")
public class MonthlyCoin {

    @Id
    private String id;

    private String userId;
    private int month;
    private int year;

    private int coinsEarned;
    private int coinsCarried;
    private int totalCoins;
}
