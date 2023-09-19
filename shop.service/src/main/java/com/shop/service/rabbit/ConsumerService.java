package com.shop.service.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class ConsumerService {

    @RabbitListener(queues = "shop-service")
    public void test(String message) throws UnsupportedEncodingException {

        System.out.println("Working");
    }
}
