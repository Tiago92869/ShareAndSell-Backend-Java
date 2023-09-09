package com.shop.service.controllers;

import com.shop.service.dto.ShopProductDto;
import com.shop.service.services.ShopProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "ShopProduct", description = "Relation between shops and products")
@RequestMapping("/shop-product")
@RestController
public class ShopProductController {

    private final ShopProductService shopProductService;

    @Autowired
    public ShopProductController(ShopProductService shopProductService) {
        this.shopProductService = shopProductService;
    }

    @GetMapping(value = "/")
    @Operation(summary = "Get all Shops_Products")
    @ResponseStatus(HttpStatus.OK)
    public Page<ShopProductDto> getAllShopProducts(Pageable pageable){

        return this.shopProductService.getAllShopProducts(pageable);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get Shops_Product by id")
    @ResponseStatus(HttpStatus.OK)
    public ShopProductDto getShopProductById(@PathVariable UUID id){

        return this.shopProductService.getShopProductById(id);
    }

    @PostMapping(value = "/")
    @Operation(summary = "Create Shops_Products")
    @ResponseStatus(HttpStatus.OK)
    public ShopProductDto createShopProduct(@RequestBody ShopProductDto shopProductDto){

        return this.shopProductService.createShopProduct(shopProductDto);
    }

    @PatchMapping(value = "/{id}")
    @Operation(summary = "Update Shops_Products")
    @ResponseStatus(HttpStatus.OK)
    public ShopProductDto updateShopProducts(@PathVariable UUID id, @RequestBody ShopProductDto shopProductDto){

        return this.shopProductService.updateShopProducts(id, shopProductDto);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete Shops_Products by id")
    @ResponseStatus(HttpStatus.OK)
    public void deleteShopProducts(@PathVariable UUID id){

        this.shopProductService.deleteShopProducts(id);
    }


}
