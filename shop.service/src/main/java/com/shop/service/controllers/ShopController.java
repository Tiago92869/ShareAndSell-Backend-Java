package com.shop.service.controllers;

import com.shop.service.domain.Shop;
import com.shop.service.dto.ShopDto;
import com.shop.service.services.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Shop", description = "Shops that are available")
@RequestMapping("/shop")
@RestController
public class ShopController {

    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping(value = "/")
    @Operation(summary = "Get all Shops")
    @ResponseStatus(HttpStatus.OK)
    public Page<ShopDto> getAllShops(Pageable pageable){

        return this.shopService.getAllShops(pageable);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get Shop by id")
    @ResponseStatus(HttpStatus.OK)
    public ShopDto getShopById(@PathVariable UUID id){

        return this.shopService.getShopById(id);
    }

    @PostMapping(value = "/")
    @Operation(summary = "Create Shop")
    @ResponseStatus(HttpStatus.OK)
    public ShopDto createShop(@RequestBody ShopDto shopDto){

        return this.shopService.createShop(shopDto);
    }

    @PatchMapping(value = "/{id}")
    @Operation(summary = "Update Shop by id")
    @ResponseStatus(HttpStatus.OK)
    public ShopDto updateShop(@PathVariable UUID id, @RequestBody ShopDto shopDto){

        return this.shopService.updateShop(id, shopDto);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete Shop by id")
    @ResponseStatus(HttpStatus.OK)
    public void deleteShop(@PathVariable UUID id){

        this.shopService.deleteShop(id);
    }
}
