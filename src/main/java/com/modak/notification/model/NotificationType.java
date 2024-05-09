package com.modak.notification.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class NotificationType {
    private String type;
    private Long intervalInSeconds;
    private Long count;

    public NotificationType(String type, Long intervalInSeconds) {
        this.type = type;
        this.intervalInSeconds = intervalInSeconds;
    }

    public void addCount() {
        this.count= this.count +1;
    }

    public void desCount() {
        this.count= this.count -1;

    }
}

