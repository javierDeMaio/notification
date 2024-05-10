package com.modak.notification.service;
import com.modak.notification.model.NotificationType;
import com.modak.notification.request.NotificationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {
    static List<NotificationType> notificationTypes = new ArrayList<>(){
        {
            add(NotificationType.builder()
                    .type("Marketing")
                    .intervalInSeconds(10800L)
                    .build());
            add(NotificationType.builder()
                    .type("Status")
                    .intervalInSeconds(30L)
                    .build());
            add(NotificationType.builder()
                    .type("News")
                    .intervalInSeconds(86400L)
                    .build());

        }
    };
    public ResponseEntity<String> send(NotificationRequest request) {
        try {
            LocalDateTime instant = LocalDateTime.now();
            for (NotificationType notificationtype : notificationTypes) {
                if (Objects.equals(request.getType(), notificationtype.getType())) {
                    if (request.getDateSent() == null) {
                        return ResponseEntity.status(HttpStatus.OK).body("Sending Notification to " + request.getRecipient());
                    } else {
                        if (request.getDateSent().plus(Duration.ofSeconds(notificationtype.getIntervalInSeconds())).isBefore(LocalDateTime.now())) {
                            return ResponseEntity.status(HttpStatus.OK).body("Sending Notification to " + request.getRecipient());
                        }
                        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Could not send more Notifications " + request.getRecipient() + " Exceded Quantity of Requests");
                    }
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body("Sending Notification to " + request.getRecipient());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in the Notification Service");
        }
    }
}



