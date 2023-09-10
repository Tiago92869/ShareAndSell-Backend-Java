package com.notification.service.repository;

import com.notification.service.domain.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface NotificationRepository extends MongoRepository<Notification, UUID> {

}
