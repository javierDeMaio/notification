package com.modak.notification.service.Impl;


import com.modak.notification.model.Gateway;
import com.modak.notification.service.NotificationService;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final Gateway gateway;
    private final Map<String, BiConsumer<String, String>> handlers;
    private final Map<String, Integer> rateLimits;
    private final Map<String, Integer> currentCounts;
    private final ScheduledExecutorService scheduler;

    public NotificationServiceImpl(Gateway gateway) {
        this.gateway = gateway;
        this.handlers = initializeHandlers();
        this.rateLimits = initializeRateLimits();
        this.currentCounts = new HashMap<>();
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    private Map<String, BiConsumer<String, String>> initializeHandlers() {
        Map<String, BiConsumer<String, String>> handlers = new HashMap<>();
        handlers.put("News", (userId, message) -> gateway.send(userId, "News: " + message));
        handlers.put("Update", (userId, message) -> gateway.send(userId, "Update: " + message));
        handlers.put("Status", (userId, message) -> gateway.send(userId, "Status: " + message));
        return handlers;
    }

    private Map<String, Integer> initializeRateLimits() {
        Map<String, Integer> rateLimits = new HashMap<>();
        rateLimits.put("News", 3);
        rateLimits.put("Update", 2);
        rateLimits.put("Status", 1);
        return rateLimits;
    }

    @Override
    public void send(String type, String userId, String message) {
        int rateLimit = rateLimits.getOrDefault(type, 0);
        int currentCount = currentCounts.getOrDefault(type, 0);

        if (currentCount < rateLimit) {
            BiConsumer<String, String> handler = handlers.get(type);
            if (handler!= null) {
                handler.accept(userId, message);
                currentCounts.put(type, currentCount + 1);

                // Schedule reset of count after a specified duration
                ScheduledFuture<?> future = scheduler.schedule(() -> {
                    currentCounts.put(type, 0);
                }, 1, TimeUnit.MINUTES); // Reset count every minute
            } else {
                throw new IllegalArgumentException("Unsupported notification type: " + type);
            }
        } else {
            throw new IllegalStateException("Rate limit exceeded for notification type: " + type);
        }
    }
}
