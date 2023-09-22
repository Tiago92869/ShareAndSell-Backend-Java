package com.notification.service.service;

import com.notification.service.domain.Notification;
import com.notification.service.dto.NotificationDto;
import com.notification.service.exceptions.EntityNotFoundException;
import com.notification.service.mapper.NotificationMapper;
import com.notification.service.rabbit.ProducerService;
import com.notification.service.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final ProducerService producerService;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, ProducerService producerService) {
        this.notificationRepository = notificationRepository;
        this.producerService = producerService;
    }

    public Page<NotificationDto> getAllNotifications(Pageable pageable) {

        this.producerService.sendMessageLogService("Get All Notifications", "45fbf752-1e87-4086-93d3-44e637c26a96");
        return this.notificationRepository.findAll(pageable).map(NotificationMapper.INSTANCE::notificationToDto);
    }

    public NotificationDto getNotificationById(UUID id) {

        Optional<Notification> optional = notificationRepository.findById(id);

        if(optional.isEmpty()){
            throw new EntityNotFoundException("A notification with that id does not exist");
        }

        this.producerService.sendMessageLogService("Get Notification with Id " + id, "45fbf752-1e87-4086-93d3-44e637c26a96");
        return NotificationMapper.INSTANCE.notificationToDto(optional.get());
    }

    public NotificationDto createNotification(NotificationDto notificationDto) {

        notificationDto.setId(UUID.randomUUID());
        notificationDto.setTimeStamp(LocalDateTime.now());

        Notification notification = NotificationMapper.INSTANCE.dtoToNotification(notificationDto);

        this.producerService.sendMessageLogService("Create Notification with Id " + notificationDto.getId(), "45fbf752-1e87-4086-93d3-44e637c26a96");
        return NotificationMapper.INSTANCE.notificationToDto(
                this.notificationRepository.save(notification));
    }

    public NotificationDto updateNotification(NotificationDto notificationDto, UUID id) {

        Optional<Notification> optional = notificationRepository.findById(id);

        if(optional.isEmpty()){
            throw new EntityNotFoundException("A notification with that id does not exist");
        }

        Notification notification = optional.get();

        if(notificationDto.getDescription() != null){

            notification.setDescription(notificationDto.getDescription());
        }

        if(notification.getTitle() != null){

            notification.setTitle(notificationDto.getTitle());
        }

        this.producerService.sendMessageLogService("Update Notification with Id " + notification.getId(), "45fbf752-1e87-4086-93d3-44e637c26a96");
        return NotificationMapper.INSTANCE.notificationToDto(this.notificationRepository.save(notification));
    }

    public void deleteNotification(UUID id) {

        Optional<Notification> optional = notificationRepository.findById(id);

        if(optional.isEmpty()){
            throw new EntityNotFoundException("A notification with that id does not exist");
        }

        this.producerService.sendMessageLogService("Delete Notification with Id " + id, "45fbf752-1e87-4086-93d3-44e637c26a96");
        this.notificationRepository.deleteById(id);
    }
}
