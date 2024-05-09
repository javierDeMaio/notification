package com.modak.notification;

import com.modak.notification.model.Notification;
import com.modak.notification.model.NotificationType;
import com.modak.notification.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
		NotificationType notificationType = new NotificationType("Status",30L);
		Notification notification = new Notification(notificationType, "recipient", "content","javierdemaio1@gmail.com", LocalDateTime.now());
		ResponseEntity<String> response = notificationService.send(notification);
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	public void testSendNotification_Success_After_Time() {
		NotificationType notificationType = new NotificationType("Status",30L);
		Notification notification = new Notification(notificationType, "recipient", "content","javierdemaio1@gmail.com", LocalDateTime.now());
		ResponseEntity<String> response = notificationService.send(notification);
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	public void testSendNotification_RateLimitExceeded() {
		NotificationType notificationType = new NotificationType("Status",300L);
		Notification notification = new Notification(notificationType, "recipient", "content","javierdemaio1@gmail.com", LocalDateTime.now());
		notification.setDateSent(LocalDateTime.now().minusSeconds(31)); // Simula un envío anterior hace más de 30 segundos
		ResponseEntity<String> response = notificationService.send(notification);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

	}


}
