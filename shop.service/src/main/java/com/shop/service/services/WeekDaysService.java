package com.shop.service.services;
import com.shop.service.repositories.WeekDaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeekDaysService {

    private final WeekDaysRepository weekDaysRepository;

    @Autowired
    public WeekDaysService(WeekDaysRepository weekDaysRepository) {
        this.weekDaysRepository = weekDaysRepository;
    }
}
