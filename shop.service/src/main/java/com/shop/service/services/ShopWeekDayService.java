package com.shop.service.services;

import com.shop.service.dto.ShopWeekDayDto;
import com.shop.service.repositories.ShopWeekDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ShopWeekDayService {

    private final ShopWeekDayRepository shopWeekDayRepository;

    @Autowired
    public ShopWeekDayService(ShopWeekDayRepository shopWeekDayRepository) {
        this.shopWeekDayRepository = shopWeekDayRepository;
    }

    public Page<ShopWeekDayDto> getAllShopWeekDay(Pageable pageable) {

        return null;
    }

    public ShopWeekDayDto createShopWeekDay(ShopWeekDayDto shopWeekDayDto) {

        return null;
    }

    public void deleteShopWeekDay(UUID id) {
    }
}
