package com.prp.budgetservice.service.impl;

import com.prp.budgetservice.dto.notification.NotificationRequestDTO;
import com.prp.budgetservice.dto.notification.NotificationResponseDTO;
import com.prp.budgetservice.entity.Notification;
import com.prp.budgetservice.repository.NotificationRepository;
import com.prp.budgetservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final ModelMapper modelMapper;

    @Override
    public NotificationResponseDTO createNotification(NotificationRequestDTO requestDTO) {
        Notification notification = modelMapper.map(requestDTO, Notification.class);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);

        return modelMapper.map(notification, NotificationResponseDTO.class);
    }

    @Transactional
    @Override
    public List<NotificationResponseDTO> getUserNotifications(String userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(notification -> modelMapper.map(notification, NotificationResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public NotificationResponseDTO markAsRead(String notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setRead(true);
        notificationRepository.save(notification);

        return modelMapper.map(notification, NotificationResponseDTO.class);
    }
}
