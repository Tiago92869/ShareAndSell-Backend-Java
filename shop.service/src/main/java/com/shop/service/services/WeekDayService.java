package com.shop.service.services;
import com.shop.service.domain.WeekDay;
import com.shop.service.dto.WeekDayDto;
import com.shop.service.maps.WeekDayMapper;
import com.shop.service.repositories.WeekDayRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WeekDayService {

    private final WeekDayRepository weekDayRepository;

    @Autowired
    public WeekDayService(WeekDayRepository weekDayRepository) {
        this.weekDayRepository = weekDayRepository;
    }

    public Page<WeekDayDto> getAllWeekDays(Pageable pageable) {

        return this.weekDayRepository.findAll(pageable).map(WeekDayMapper.INSTANCE::weekDaysToDto);
    }

    @PostConstruct
    public void createDefaultValues(){

        if(this.weekDayRepository.count() == 0){

            List<WeekDay> weekDayList = new ArrayList<>();

            weekDayList.add(new WeekDay(UUID.randomUUID(), "Monday"));
            weekDayList.add(new WeekDay(UUID.randomUUID(), "Tuesday"));
            weekDayList.add(new WeekDay(UUID.randomUUID(), "Wednesday"));
            weekDayList.add(new WeekDay(UUID.randomUUID(), "Thursday"));
            weekDayList.add(new WeekDay(UUID.randomUUID(), "Friday"));
            weekDayList.add(new WeekDay(UUID.randomUUID(), "Saturday"));
            weekDayList.add(new WeekDay(UUID.randomUUID(), "Sunday"));

            this.weekDayRepository.saveAll(weekDayList);
        }
    }
}
