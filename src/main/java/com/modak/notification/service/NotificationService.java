package com.modak.notification.service;
import com.modak.notification.model.Notification;
import com.modak.notification.model.NotificationType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
    public ResponseEntity<String> send(Notification notification) {
        LocalDateTime instant = LocalDateTime.now();
        for(NotificationType notificationtype: notificationTypes) {
           if(Objects.equals(notification.getNotificationType().getType(), notificationtype.getType())){
               if(notification.getDateSent()==null){
                   notification.setDateSent(LocalDateTime.now());
                   return ResponseEntity.status(HttpStatus.OK).body("Sending Notification to " + notification.getRecipient());
               }else{
                   if((instant.minusSeconds(notification.getNotificationType().getIntervalInSeconds())).isAfter(notification.getDateSent())) {
                       return ResponseEntity.status(HttpStatus.OK).body("Sending Notification to " + notification.getRecipient());
                   }
                   return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not send more Notifications " + notification.getRecipient()+" Exceded Quantity of Requests");
               }
           }
        }
        return ResponseEntity.status(HttpStatus.OK).body("Sending Notification to " + notification.getRecipient());
    }

}

