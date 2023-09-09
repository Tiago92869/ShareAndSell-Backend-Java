package com.shop.service.services;
import com.shop.service.dto.ShopProductDto;
import com.shop.service.repositories.ShopProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ShopProductService {

    private final ShopProductRepository shopProductRepository;

    @Autowired
    public ShopProductService(ShopProductRepository shopProductRepository) {
        this.shopProductRepository = shopProductRepository;
    }

    public Page<ShopProductDto> getAllShopProducts(Pageable pageable) {
        return null;
    }

    public ShopProductDto getShopProductById(UUID id) {

        return null;
    }

    public ShopProductDto createShopProduct(ShopProductDto shopProductDto) {

        return null;
    }

    public ShopProductDto updateShopProducts(UUID id, ShopProductDto shopProductDto) {

        return null;
    }

    public void deleteShopProducts() {
    }
}
