package com.shop.service.controllers;

import com.shop.service.dto.WeekDayDto;
import com.shop.service.services.WeekDayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "WeekDays", description = "Days of the Week")
@RequestMapping("/week-days")
@RestController
public class WeekDayController {

    private final WeekDayService weekDayService;

    @Autowired
    public WeekDayController(WeekDayService weekDayService) {
        this.weekDayService = weekDayService;
    }

    @GetMapping("/")
    @Operation(summary = "List all days of the week")
    @ResponseStatus(HttpStatus.OK)
    public Page<WeekDayDto> getAllWeekDays(Pageable pageable){

        return this.weekDayService.getAllWeekDays(pageable);
    }

}
