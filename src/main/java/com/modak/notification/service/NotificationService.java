package com.modak.notification.service;


public interface NotificationService {
    /**
     * Sends a notification of the specified type to the given user.
     *
     * @param type    the type of notification
     * @param userId  the ID of the user to whom the notification will be sent
     * @param message the content of the notification
     * @throws IllegalArgumentException if the specified notification type is not supported
     * @throws IllegalStateException    if the rate limit for the notification type has been exceeded
     */
   void send(String type, String userId, String message);
}


