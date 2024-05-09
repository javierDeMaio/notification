package com.modak.notification.controller;

import com.modak.notification.model.Notification;
import com.modak.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.OptionalLong;

@RestController
@Slf4j
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService service;

    @GetMapping("/send")
    public ResponseEntity<String> send(Notification notification){
        try{
            return service.send(notification);
        } catch(Exception e){
            log.error("Error Sending Notification",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Sending Notification");
        }
    }
}
