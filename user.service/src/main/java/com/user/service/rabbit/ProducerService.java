package com.user.service.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    private String userDefault = "User-Service: ";

    private final RabbitTemplate rabbitTemplate;

    public ProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend("user-service", message);
    }

    public void deleteAppointmentByUserId(String message) {
        rabbitTemplate.convertAndSend("shop-service-user-appointment", message);
    }

    public void sendMessageLogService(String message, String userId){
        MessageProperties properties = new MessageProperties();
        properties.setHeader("UserId", userId);
        properties.setHeader("Content", userDefault + message);
        Message msg = new Message(userId.getBytes(), properties);
        rabbitTemplate.send("logs-service-create", msg);
    }
}
