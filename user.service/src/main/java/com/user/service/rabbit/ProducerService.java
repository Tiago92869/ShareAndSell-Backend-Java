package com.user.service.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

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
}
