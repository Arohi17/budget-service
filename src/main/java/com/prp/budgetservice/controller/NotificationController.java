package com.prp.budgetservice.controller;


import com.prp.budgetservice.dto.notification.NotificationRequestDTO;
import com.prp.budgetservice.dto.notification.NotificationResponseDTO;
import com.prp.budgetservice.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // Create a new notification
    @PostMapping
    public ResponseEntity<NotificationResponseDTO> createNotification(
            @RequestBody @Valid NotificationRequestDTO requestDTO) {

        NotificationResponseDTO response = notificationService.createNotification(requestDTO);
        return ResponseEntity.ok(response);
    }

    // Get all notifications
    @GetMapping("/{userId}")
    public ResponseEntity<List<NotificationResponseDTO>> getUserNotifications(
            @PathVariable String userId) {

        List<NotificationResponseDTO> response = notificationService.getUserNotifications(userId);
        return ResponseEntity.ok(response);
    }

    // Mark notification as read
    @PutMapping("/{id}/read")
    public ResponseEntity<NotificationResponseDTO> markAsRead(
            @PathVariable String id) {

        NotificationResponseDTO response = notificationService.markAsRead(id);
        return ResponseEntity.ok(response);
    }

}
