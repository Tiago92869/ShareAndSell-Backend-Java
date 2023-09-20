package com.shop.service.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProducerService {

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
}
