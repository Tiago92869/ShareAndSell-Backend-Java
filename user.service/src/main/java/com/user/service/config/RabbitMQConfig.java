package com.user.service.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue userQueue() {
        return new Queue("user-service");
    }

    @Bean
    public Queue userQueueShopDelete() {
        return new Queue("user-service-shop-delete");
    }
}