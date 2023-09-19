package com.shop.service.rabbit;

import com.shop.service.services.AppointmentService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Service
public class ConsumerService {

    private final AppointmentService appointmentService;

    @Autowired
    public ConsumerService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @RabbitListener(queues = "shop-service")
    public void test(String message) throws UnsupportedEncodingException {

        System.out.println("Working");
    }

    @RabbitListener(queues = "shop-service-user-appointment")
    public void deleteAppointmentByUserId(String userId) {

        appointmentService.deleteAppointmentByUserId(UUID.fromString(userId));
    }
}
