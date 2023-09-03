package com.shop.service.services;
import com.shop.service.repositories.ShopProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopProductService {

    private final ShopProductRepository shopProductRepository;

    @Autowired
    public ShopProductService(ShopProductRepository shopProductRepository) {
        this.shopProductRepository = shopProductRepository;
    }
}
