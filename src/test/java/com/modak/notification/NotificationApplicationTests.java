package com.modak.notification;

import com.modak.notification.controller.NotificationController;
import com.modak.notification.model.Notification;
import com.modak.notification.model.NotificationType;
import com.modak.notification.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class NotificationApplicationTests {

	@Test
	void contextLoads() {
	}
	private NotificationService notificationService;

	@BeforeEach
	public void setUp() {
		notificationService = new NotificationService();
	}

	@Test
	public void testSendNotification_Success() {
		NotificationType notificationType = NotificationType.builder()
				.type("Status")
				.intervalInSeconds(30L)
				.build();
		Notification notification = new Notification(notificationType, "recipient", "content","javierdemaio1@gmail.com", null);
		ResponseEntity<String> response = notificationService.send(notification);
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	public void testSendNotification_Success_After() {
		NotificationType notificationType = NotificationType.builder()
				.type("News")
				.intervalInSeconds(86400L)
				.build();
		Notification notification = new Notification(notificationType, "recipient", "content","javierdemaio1@gmail.com", LocalDateTime.now().minusSeconds(86401L));
		ResponseEntity<String> response = notificationService.send(notification);
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	public void testSendNotification_RateLimitExceeded() {
		NotificationType notificationType = NotificationType.builder()
				.type("News")
				.intervalInSeconds(86400L)
				.build();
		Notification notification = new Notification(notificationType, "recipient", "content","javierdemaio1@gmail.com", LocalDateTime.now());
		notification.setDateSent(LocalDateTime.now()); // Simula un envío anterior hace más de 30 segundos
		ResponseEntity<String> response = notificationService.send(notification);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

	}


}
