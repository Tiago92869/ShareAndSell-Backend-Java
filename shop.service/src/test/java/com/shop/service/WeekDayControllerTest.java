package com.shop.service;

import com.shop.service.domain.WeekDay;
import com.shop.service.dto.WeekDayDto;
import com.shop.service.maps.WeekDayMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
public class WeekDayControllerTest {

    @Test
    public void testWeekDayToDto(){

        WeekDay weekDay = new WeekDay(UUID.randomUUID(), "Monday");
        WeekDayDto weekDayDto = WeekDayMapper.INSTANCE.weekDaysToDto(weekDay);

        assertNotNull(weekDayDto);
        assertSame("Monday", weekDayDto.getName());
    }
}
