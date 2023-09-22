package com.shop.service.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProducerService {

    private String shopDefault = "Shop-Service: ";
    private final RabbitTemplate rabbitTemplate;

    public ProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend("shop-service", message);
    }

    public void sendMessageUserServiceShopDelete(UUID shopId){

        rabbitTemplate.convertAndSend("user-service-shop-delete", shopId);
    }

    public void sendMessageLogService(String message, String userId){
        MessageProperties properties = new MessageProperties();
        properties.setHeader("UserId", userId);
        properties.setHeader("Content", shopDefault + message);
        Message msg = new Message(userId.getBytes(), properties);
        rabbitTemplate.send("logs-service-create", msg);
    }
}
