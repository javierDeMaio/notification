package com.modak.notification.service;

import com.modak.notification.model.Notification;
import com.modak.notification.model.NotificationType;
import jdk.jshell.Snippet;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class NotificationService {

    static List<NotificationType> notificationTypes = new ArrayList<>(){
        {
            add(new NotificationType("Status",30L));
            add(new NotificationType("News",86400L));
            add(new NotificationType("Marketing",10800L));
        }
    };

    public ResponseEntity<String> send(Notification notification) {
        LocalDateTime instant = LocalDateTime.now();
        for(NotificationType notificationtype: notificationTypes) {
           if(notification.getNotificationType()==notificationtype){
               if(notification.getDateSent()==null){
                   notification.setDateSent(LocalDateTime.now());
                   notification.getNotificationType().addCount();
                   return ResponseEntity.status(HttpStatus.OK).body("Sending Notification to " + notification.getRecipient());
               }else{
                   if((instant.minusSeconds(notification.getNotificationType().getIntervalInSeconds())).isAfter(notification.getDateSent())) {
                       notification.getNotificationType().addCount();
                       return ResponseEntity.status(HttpStatus.OK).body("Sending Notification to " + notification.getRecipient());
                   }
                   return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not send more Notifications" + notification.getRecipient()+" Exceded Quantity of Requests");
               }
           }
        }
        notification.getNotificationType().addCount();
        return ResponseEntity.status(HttpStatus.OK).body("Sending Notification to " + notification.getRecipient());
    }
}

