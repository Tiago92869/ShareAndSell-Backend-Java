package com.user.service.rabbit;

import com.user.service.services.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Service
public class ConsumerService {

    private final UserService userService;

    @Autowired
    public ConsumerService(UserService userService) {
        this.userService = userService;
    }

    @RabbitListener(queues = "user-service")
    public void test(String message) throws UnsupportedEncodingException {

        System.out.println("Working");
    }

    @RabbitListener(queues = "user-service-shop-delete")
    public void deleteShopFromFavorites(UUID shopId){
        System.out.println("Hello");
        try{
            this.userService.removeShopIdFromFavorites(shopId);

        }catch (Exception e){
            System.out.println("Exception found when deleting shopId: " + e);
        }
    }
}
