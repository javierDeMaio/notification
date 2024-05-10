package com.modak.notification;


import com.modak.notification.model.Gateway;
import com.modak.notification.service.Impl.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class NotificationServiceImplTest {

	@Mock
	private Gateway gateway;

	private NotificationServiceImpl notificationService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		notificationService = new NotificationServiceImpl(gateway);
	}

	@Test
	public void testSendNotification_Success() {

		String type = "News";
		String userId = "user123";
		String message = "Test message";

		// Send notification three times (within rate limit)
		notificationService.send(type, userId, message);
		notificationService.send(type, userId, message);
		notificationService.send(type, userId, message);

		// Verify that notifications were sent
		verify(gateway, times(3)).send(userId, "News: " + message);
	}


	@Test
	public void testSendNotification_UnsupportedType() {
		String type = "invalidType";
		String userId = "user123";
		String message = "Test message";

		try {
			notificationService.send(type, userId, message);
		} catch (IllegalStateException e) {
			// Expected exception
			return;
		}

		// If no exception is thrown, fail the test
		fail("Expected IllegalStateException was not thrown.");
	}
}

