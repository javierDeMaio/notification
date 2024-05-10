package com.modak.notification.model;

import org.springframework.stereotype.Component;

@Component
public class Gateway {
    /* already implemented */
    public void send(String userId, String message) {
        System.out.println("sending message to user " + userId);
    }
}