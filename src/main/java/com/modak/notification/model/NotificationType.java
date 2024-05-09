package com.modak.notification.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationType {
    private String type;
    private Long intervalInSeconds;

}

