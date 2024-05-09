package com.modak.notification.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter

public class Notification {
    private NotificationType notificationType;
    private String content;
    private String title;
    private String recipient;
    private LocalDateTime dateSent;

    public Notification(NotificationType notificationType, String content, String title, String recipient, LocalDateTime dateSent) {
        this.notificationType = notificationType;
        this.content = content;
        this.title = title;
        this.recipient = recipient;
        this.dateSent = dateSent;
    }

}
