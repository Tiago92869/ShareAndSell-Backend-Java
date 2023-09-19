package com.shop.service.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue shopQueue() {
        return new Queue("shop-service");
    }

    @Bean
    public Queue shopAppointmnetUserQueue() {
        return new Queue("shop-service-user-appointment");
    }
}