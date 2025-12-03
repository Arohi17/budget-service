package com.prp.budgetservice.service;

import com.prp.budgetservice.dto.notification.NotificationRequestDTO;
import com.prp.budgetservice.dto.notification.NotificationResponseDTO;

import java.util.List;

public interface NotificationService {

    NotificationResponseDTO createNotification(NotificationRequestDTO requestDTO);

    List<NotificationResponseDTO> getUserNotifications(String userId);

    NotificationResponseDTO markAsRead(String notificationId);
}
