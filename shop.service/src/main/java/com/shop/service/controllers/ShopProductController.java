package com.shop.service.controllers;

import com.shop.service.services.ShopProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ShopProduct", description = "Relation between shops and products")
@RequestMapping("/shop-product")
@RestController
public class ShopProductController {

    private final ShopProductService shopProductService;

    @Autowired
    public ShopProductController(ShopProductService shopProductService) {
        this.shopProductService = shopProductService;
    }
}
