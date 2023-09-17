package com.notification.service;

import com.notification.service.domain.Notification;
import com.notification.service.dto.NotificationDto;
import com.notification.service.mapper.NotificationMapper;
import com.notification.service.repository.NotificationRepository;
import com.notification.service.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TestNotificationService {

    @Mock
    private NotificationRepository notificationRepository;

    private NotificationService notificationService;

    private final Notification sampleNotification = new Notification(UUID.fromString("fd2a2b95-518f-45c6-8985-c599fc4cfc19"),
            LocalDateTime.now(), "Notification 1", "This is notification number 1");

    private final Notification sampleNotification2 = new Notification(UUID.fromString("4fa4a525-0101-47fe-999c-bedc0671490b"),
            LocalDateTime.now(), "Notification 2", "This is notification number 2");

    private final NotificationDto sampleNotificationDto = new NotificationDto(
            UUID.fromString("fd2a2b95-518f-45c6-8985-c599fc4cfc19"), LocalDateTime.now(), "Notification 1",
            "This is notification number 1");

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        notificationService = new NotificationService(notificationRepository);
    }

    @Test
    public void testNotificationToDto(){

        NotificationDto result = NotificationMapper.INSTANCE.notificationToDto(sampleNotification);

        assertNotNull(result);
        assertEquals(result.getDescription(), sampleNotification.getDescription());
        assertEquals(result.getId(), sampleNotification.getId());
    }

    @Test
    public void testDtoToNotification(){

        Notification result = NotificationMapper.INSTANCE.dtoToNotification(sampleNotificationDto);

        assertNotNull(result);
        assertEquals(result.getDescription(), sampleNotificationDto.getDescription());
        assertEquals(result.getId(), sampleNotificationDto.getId());
    }

    @Test
    public void testGetAllNotifications(){

        when(notificationRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(sampleNotification, sampleNotification2)));

        Page<NotificationDto> result = notificationService.getAllNotifications(PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    public void testGetNotificationById(){

        when(notificationRepository.findById(UUID.fromString("a9c6998f-e346-4fee-a451-8290bef086fd")))
                .thenReturn(Optional.of(sampleNotification));

        NotificationDto result = notificationService.getNotificationById(UUID.fromString("a9c6998f-e346-4fee-a451-8290bef086fd"));

        assertNotNull(result);
        assertEquals(result.getDescription(), sampleNotification.getDescription());
    }

    @Test
    public void testCreateNotification(){

        when(notificationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        NotificationDto result = notificationService.createNotification(sampleNotificationDto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(result.getDescription(), sampleNotificationDto.getDescription());
    }

    @Test
    public void testUpdateNotification(){

        when(notificationRepository.findById(sampleNotificationDto.getId())).thenReturn(Optional.of(sampleNotification));

        when(notificationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        NotificationDto result = notificationService.updateNotification(sampleNotificationDto, sampleNotificationDto.getId());

        assertNotNull(result);
        assertEquals(result.getDescription(), sampleNotificationDto.getDescription());
    }

    @Test
    public void testDeleteNotification(){

        when(notificationRepository.findById(sampleNotificationDto.getId())).thenReturn(Optional.of(sampleNotification));

        assertDoesNotThrow(() -> notificationService.deleteNotification(sampleNotificationDto.getId()));
    }
}
