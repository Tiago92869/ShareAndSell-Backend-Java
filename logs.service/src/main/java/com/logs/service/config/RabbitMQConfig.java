package com.logs.service.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue logQueue() {
        return new Queue("logs-service");
    }

    @Bean
    public Queue logQueueCreate() {
        return new Queue("logs-service-create");
    }
}