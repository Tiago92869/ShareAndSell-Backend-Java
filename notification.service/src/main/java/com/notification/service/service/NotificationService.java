package com.notification.service.service;

import com.notification.service.domain.Notification;
import com.notification.service.dto.NotificationDto;
import com.notification.service.exceptions.EntityNotFoundException;
import com.notification.service.mapper.NotificationMapper;
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

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Page<NotificationDto> getAllNotifications(Pageable pageable) {

        return this.notificationRepository.findAll(pageable).map(NotificationMapper.INSTANCE::notificationToDto);
    }

    public NotificationDto getNotificationById(UUID id) {

        Optional<Notification> optional = notificationRepository.findById(id);

        if(optional.isEmpty()){
            throw new EntityNotFoundException("A notification with that id does not exist");
        }

        return NotificationMapper.INSTANCE.notificationToDto(optional.get());
    }

    public NotificationDto createNotification(NotificationDto notificationDto) {

        notificationDto.setId(UUID.randomUUID());
        notificationDto.setTimeStamp(LocalDateTime.now());

        Notification notification = NotificationMapper.INSTANCE.dtoToNotification(notificationDto);

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

        return NotificationMapper.INSTANCE.notificationToDto(this.notificationRepository.save(notification));
    }

    public void deleteNotification(UUID id) {

        Optional<Notification> optional = notificationRepository.findById(id);

        if(optional.isEmpty()){
            throw new EntityNotFoundException("A notification with that id does not exist");
        }

        this.notificationRepository.deleteById(id);
    }
}
