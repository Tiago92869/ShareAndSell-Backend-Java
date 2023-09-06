package com.shop.service;

import com.shop.service.controllers.WeekDayController;
import com.shop.service.domain.WeekDay;
import com.shop.service.dto.WeekDayDto;
import com.shop.service.maps.WeekDayMapper;
import com.shop.service.services.WeekDayService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = WeekDayController.class)
@WithMockUser
public class WeekDayControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeekDayService weekDayService;

    WeekDay weekDay = new WeekDay(UUID.fromString("708be42e-e841-4c0e-8ee2-61844424393d"), "Monday");

    String exampleWeekDayJson = "{\"id\": \"708be42e-e841-4c0e-8ee2-61844424393d\", \"name\": \"Monday\"}";

    @Test
    @Order(1)
    public void testWeekDayToDto(){

        WeekDayDto weekDayDto = WeekDayMapper.INSTANCE.weekDaysToDto(weekDay);

        assertNotNull(weekDayDto);
        assertSame("Monday", weekDayDto.getName());
    }

    @Test
    public void testGetAllWeekDays() throws Exception {
        //Given
        PageRequest pageRequest = PageRequest.of(0, 1);
        List<WeekDayDto> weekDayList = new ArrayList<>();
        weekDayList.add(WeekDayMapper.INSTANCE.weekDaysToDto(weekDay));
        Page<WeekDayDto> weekDayPage = new PageImpl<>(weekDayList, pageRequest, weekDayList.size());

        Mockito.when(weekDayService.getAllWeekDays(pageRequest)).thenReturn(weekDayPage);

        //When
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/week-days/?page=0&size=1").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //Then
        MockMvcResultMatchers.status().isOk().match(result);

        // Assert content field only
        mockMvc.perform(requestBuilder)
                .andExpect(jsonPath("$.content[0].id").value("708be42e-e841-4c0e-8ee2-61844424393d"))
                .andExpect(jsonPath("$.content[0].name").value("Monday"));
    }
}
