package com.modak.notification.controller;

import com.modak.notification.request.NotificationRequest;
import com.modak.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService service;

    @PostMapping("/send")
    public void send(@RequestBody NotificationRequest request){
        try{
            service.send(request);
        } catch(Exception e){
            log.error(e.getMessage());
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Sending Notification");
        }
  }
}
