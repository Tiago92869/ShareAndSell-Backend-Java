package com.shop.service.controllers;

import com.shop.service.services.WeekDaysService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "WeekDays", description = "Days of the Week")
@RequestMapping("/week-days")
@RestController
public class WeekDaysController {

    private final WeekDaysService weekDaysService;

    @Autowired
    public WeekDaysController(WeekDaysService weekDaysService) {
        this.weekDaysService = weekDaysService;
    }
}
