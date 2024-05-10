package com.modak.notification;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.modak.notification.controller.NotificationController;
import com.modak.notification.model.Notification;
import com.modak.notification.model.NotificationType;
import com.modak.notification.request.NotificationRequest;
import com.modak.notification.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
		ResponseEntity<String> response = notificationService.send(getRequest2());
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	public void testSendNotification_Success_After() {
		ResponseEntity<String> response = notificationService.send(getRequest());
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	public void testSendNotification_RateLimitExceeded() {
		ResponseEntity<String> response = notificationService.send(getRequest3());
		assertEquals(HttpStatus.TOO_MANY_REQUESTS, response.getStatusCode());

	}

	private NotificationRequest getRequest(){
		NotificationRequest request = new NotificationRequest();
		request.setType("Status");
		request.setRecipient("Javierdemaio1@gmail.com");
		request.setDateSent(LocalDateTime.now().minus(100000L, ChronoUnit.SECONDS));
		return request;
	}
	private NotificationRequest getRequest2(){
		NotificationRequest request = new NotificationRequest();
		request.setType("Status");
		request.setRecipient("Javierdemaio1@gmail.com");
		request.setDateSent(null);
		return request;
	}
	private NotificationRequest getRequest3(){
		NotificationRequest request = new NotificationRequest();
		request.setType("News");
		request.setRecipient("Javierdemaio1@gmail.com");
		request.setDateSent(LocalDateTime.now());
		return request;
	}
}


