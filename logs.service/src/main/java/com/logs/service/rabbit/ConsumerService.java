package com.logs.service.rabbit;

import com.logs.service.dto.LogDto;
import com.logs.service.service.LogService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Service
public class ConsumerService {

    private final LogService logService;

    @Autowired
    public ConsumerService(LogService logService) {
        this.logService = logService;
    }

    @RabbitListener(queues = "logs-service")
    public void test(String message) throws UnsupportedEncodingException {

        System.out.println("Working");
    }

    @RabbitListener(queues = "logs-service-create")
    public void createLog(Message message) {

        String userId = (String) message.getMessageProperties().getHeaders().get("UserId");
        String content = (String) message.getMessageProperties().getHeaders().get("Content");

        LogDto logDto = new LogDto();
        logDto.setUserId(UUID.fromString(userId));
        logDto.setDescription(content);

        this.logService.createLog(logDto);

    }


}
