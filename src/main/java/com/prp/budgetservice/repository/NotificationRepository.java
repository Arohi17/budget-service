package com.prp.budgetservice.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.prp.budgetservice.entity.Notification;

import java.util.List;

public interface NotificationRepository extends ArangoRepository<Notification, String> {

    List<Notification> findByUserIdOrderByCreatedAtDesc(String userId);

    List<Notification> findByUserIdAndIsReadFalseOrderByCreatedAtDesc(String userId);
}
