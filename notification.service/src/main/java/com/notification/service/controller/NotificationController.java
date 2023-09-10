package com.notification.service.controller;

import com.notification.service.dto.NotificationDto;
import com.notification.service.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Notification", description = "Manage Notifications")
@RequestMapping("/notification")
@RestController
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/")
    @Operation(summary = "Get all notifications")
    @ResponseStatus(HttpStatus.OK)
    public Page<NotificationDto> getAllNotifications(Pageable pageable){

        return this.notificationService.getAllNotifications(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get notification by id")
    @ResponseStatus(HttpStatus.OK)
    public NotificationDto getAllNotifications(@PathVariable UUID id){

        return this.notificationService.getNotificationById(id);
    }

    @PostMapping("/")
    @Operation(summary = "Create notification")
    @ResponseStatus(HttpStatus.OK)
    public NotificationDto createNotification(@RequestBody NotificationDto notificationDto){

        return this.notificationService.createNotification(notificationDto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update notification")
    @ResponseStatus(HttpStatus.OK)
    public NotificationDto updateNotification(@RequestBody NotificationDto notificationDto, @PathVariable UUID id){

        return this.notificationService.updateNotification(notificationDto, id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete notification")
    @ResponseStatus(HttpStatus.OK)
    public void deleteNotification(@PathVariable UUID id){

        this.notificationService.deleteNotification(id);
    }

}
