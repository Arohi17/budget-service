package com.prp.budgetservice.dto.notification;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDTO {

    private String id;
    private String userId;
    private String title;
    private String message;
    private Boolean read = false;
    private LocalDateTime createdAt;
}
