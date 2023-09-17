package com.notification.service;

import com.notification.service.controller.NotificationController;
import com.notification.service.dto.NotificationDto;
import com.notification.service.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NotificationController.class)
public class TestNotificationController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    private final NotificationDto sampleNotificationDto = new NotificationDto(
            UUID.fromString("fd2a2b95-518f-45c6-8985-c599fc4cfc19"), LocalDateTime.now(), "Notification 1",
            "This is notification number 1");

    private final NotificationDto sampleNotificationDto2 = new NotificationDto(
            UUID.fromString("945df2e4-24ff-4d21-ba2f-498c7146f651"), LocalDateTime.now(), "Notification 2",
            "This is notification number 2");

    private final String sampleNotificationJson = "{\"title\": \"Notification 1\"," +
            "\"description\": \"This is notification number 1\"}";

    @Test
    public void testGetAllNotifications() throws Exception {

        when(notificationService.getAllNotifications(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(sampleNotificationDto, sampleNotificationDto2)));

        mockMvc.perform(MockMvcRequestBuilders.get("/notification/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(2)));
    }

    @Test
    public void testGetNotificationById() throws Exception {

        when(notificationService.getNotificationById(any(UUID.class))).thenReturn(sampleNotificationDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/notification/{id}", sampleNotificationDto.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(String.valueOf(sampleNotificationDto.getId())));
    }

    @Test
    public void testCreateNotification() throws Exception {

        when(notificationService.createNotification(any(NotificationDto.class))).thenReturn(sampleNotificationDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/notification/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleNotificationJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description")
                        .value(String.valueOf(sampleNotificationDto.getDescription())));
    }

    @Test
    public void testUpdateNotification() throws Exception {

        when(notificationService.updateNotification(any(NotificationDto.class), any(UUID.class))).thenReturn(sampleNotificationDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/notification/{id}", sampleNotificationDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleNotificationJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description")
                        .value(String.valueOf(sampleNotificationDto.getDescription())));
    }

    @Test
    public void testDeleteNotification() throws Exception {

        doNothing().when(notificationService).deleteNotification(any(UUID.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/notification/{id}", sampleNotificationDto.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
