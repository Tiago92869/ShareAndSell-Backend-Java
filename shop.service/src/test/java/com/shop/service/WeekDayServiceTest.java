package com.shop.service;

import com.shop.service.domain.WeekDay;
import com.shop.service.dto.WeekDayDto;
import com.shop.service.repositories.WeekDayRepository;
import com.shop.service.services.WeekDayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class WeekDayServiceTest {

    @Mock
    private WeekDayRepository weekDayRepository;

    private WeekDayService weekDayService;

    WeekDay weekDay = new WeekDay(UUID.fromString("708be42e-e841-4c0e-8ee2-61844424393d"), "Monday");

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        weekDayService = new WeekDayService(weekDayRepository);
    }

    @Test
    public void testGetAllWeekDays() {
        //Given
        PageRequest pageRequest = PageRequest.of(0, 1);
        List<WeekDay> weekDayList = new ArrayList<>();
        weekDayList.add(weekDay);
        Page<WeekDay> weekDayPage = new PageImpl<>(weekDayList, pageRequest, weekDayList.size());

        Mockito.when(weekDayRepository.findAll(pageRequest)).thenReturn(weekDayPage);

        // When
        Page<WeekDayDto> resultPage = weekDayService.getAllWeekDays(pageRequest);

        // Then
        assertEquals(1, resultPage.getTotalElements());
        assertEquals("Monday", resultPage.getContent().get(0).getName());
    }
}
