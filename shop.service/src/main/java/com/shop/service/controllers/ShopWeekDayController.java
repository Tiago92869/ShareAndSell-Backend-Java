package com.shop.service.controllers;

import com.shop.service.dto.ShopWeekDayDto;
import com.shop.service.services.ShopWeekDayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "ShopWeekDay", description = "Relation between shops and weekDays")
@RequestMapping("/shop-week-day")
@RestController
public class ShopWeekDayController {

    private final ShopWeekDayService shopWeekDayService;

    @Autowired
    public ShopWeekDayController(ShopWeekDayService shopWeekDayService) {
        this.shopWeekDayService = shopWeekDayService;
    }

    @GetMapping(value = "/")
    @Operation(summary = "Get all ShopWeekDays")
    @ResponseStatus(HttpStatus.OK)
    public Page<ShopWeekDayDto> getAllShopWeekDay(Pageable pageable){

        return this.shopWeekDayService.getAllShopWeekDay(pageable);
    }

    @PostMapping(value = "/")
    @Operation(summary = "Create ShopWeekDays")
    @ResponseStatus(HttpStatus.OK)
    public ShopWeekDayDto createShopWeekDay(@RequestBody ShopWeekDayDto shopWeekDayDto){

        return this.shopWeekDayService.createShopWeekDay(shopWeekDayDto);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete ShopWeekDays by id")
    @ResponseStatus(HttpStatus.OK)
    public void deleteShopWeekDay(@PathVariable UUID id){

        this.shopWeekDayService.deleteShopWeekDay(id);
    }


}
