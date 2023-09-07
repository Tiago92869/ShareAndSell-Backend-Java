package com.shop.service.services;
import com.shop.service.dto.ShopDto;
import com.shop.service.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ShopService {

    private final ShopRepository shopRepository;

    @Autowired
    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public Page<ShopDto> getAllShops(Pageable pageable) {

        return null;
    }

    public ShopDto getShopById(UUID id) {

        return null;
    }

    public ShopDto createShop(ShopDto shopDto) {

        return null;
    }

    public ShopDto updateShop(UUID id, ShopDto shopDto) {

        return null;
    }

    public void deleteShop(UUID id) {


    }
}
