package com.modak.notification.request;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationRequest {
    private String type;
    private String userId;
    private String message;

}
