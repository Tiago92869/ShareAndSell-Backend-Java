package com.notification.service.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Notification", description = "Manage Notifications")
@RequestMapping("/notification")
@RestController
public class NotificationController {

}
