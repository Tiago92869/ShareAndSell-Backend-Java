package com.notification.service.mapper;

import com.notification.service.domain.Notification;
import com.notification.service.dto.NotificationDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NotificationMapper {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    NotificationDto notificationToDto(Notification notification);

    Notification dtoToNotification(NotificationDto notificationDto);
}
