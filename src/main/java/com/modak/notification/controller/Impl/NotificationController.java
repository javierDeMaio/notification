package com.modak.notification.controller.Impl;

import com.modak.notification.request.NotificationRequest;
import com.modak.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/sendNotification")
    public String sendNotification(@RequestBody NotificationRequest request) {
        try {
            notificationService.send(request.getType(), request.getUserId(), request.getMessage());
            return "Notification sent successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        } catch (IllegalStateException e) {
            return "Error: " + e.getMessage();
        }
    }
}
