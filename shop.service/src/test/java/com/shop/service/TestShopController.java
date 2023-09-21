package com.shop.service;

import com.shop.service.controllers.ShopController;
import com.shop.service.domain.WeekDays;
import com.shop.service.dto.ShopDto;
import com.shop.service.services.ShopService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ShopController.class)
public class TestShopController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopService shopService;

    private final List<WeekDays> weekDaysList = new ArrayList<>(Collections.singleton(WeekDays.MONDAY));

    private final List<WeekDays> weekDaysList2 = new ArrayList<>(Collections.singleton(WeekDays.SUNDAY));

    private final List<String> weekDayListString = List.of("MONDAY");

    private final ShopDto sampleShopDto = new ShopDto(UUID.fromString("1604ddbc-93f6-4a12-b497-8f0ec795e33a"), "Shop1",
            "Shop number 1", "Bankers Street", "Braga", "Portugal", "+351485265471",
            (float) 4.4, LocalTime.now().minusHours(2), LocalTime.now().minusHours(1),
            true, weekDaysList);

    private final ShopDto sampleShopDto2 = new ShopDto(UUID.fromString("22447e5f-1832-468f-9583-d9576f0f6342"), "Shop2",
            "Shop number 2", "Celine Street", "Porto", "Portugal", "+351958471523",
            (float) 2.4, LocalTime.now().minusHours(3), LocalTime.now().minusHours(2),
            false, weekDaysList2);

    private final String sampleShopJson = "{\"name\": \"Shop1\"," +
            "\"description\": \"Shop number 1\"," +
            "\"address\": \"Bankers Street\"," +
            "\"city\": \"Braga\"," +
            "\"country\": \"Portugal\"," +
            "\"phoneNumber\": \"+351485265471\"," +
            "\"startTime\": \"10:38:25\"," +
            "\"endTime\": \"11:38:25\"," +
            "\"isEnable\": true," +
            "\"weekDays\": [\"MONDAY\"]}";

    @Test
    public void testGetAllRatings() throws Exception {

        when(shopService.getAllShops(any(Pageable.class), eq(null), eq(null), eq(null)))
                .thenReturn(new PageImpl<>(List.of(sampleShopDto, sampleShopDto2)));

        mockMvc.perform(MockMvcRequestBuilders.get("/shop/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(2)));
    }

    @Test
    public void testGetAllRatingsEnable() throws Exception {

        when(shopService.getAllShops(any(Pageable.class), eq(null), eq(Boolean.TRUE), eq(null)))
                .thenReturn(new PageImpl<>(List.of(sampleShopDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/shop/")
                        .param("Enable", String.valueOf(Boolean.TRUE)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetAllRatingsEnableWeek() throws Exception {

        when(shopService.getAllShops(any(Pageable.class), eq(weekDayListString), eq(Boolean.TRUE), eq(null)))
                .thenReturn(new PageImpl<>(List.of(sampleShopDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/shop/")
                        .param("Day of the Week", "MONDAY")
                        .param("Enable", String.valueOf(Boolean.TRUE)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetAllRatingsEnableSearch() throws Exception {

        when(shopService.getAllShops(any(Pageable.class), eq(null), eq(Boolean.TRUE), eq("Shop1")))
                .thenReturn(new PageImpl<>(List.of(sampleShopDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/shop/")
                        .param("Enable", String.valueOf(Boolean.TRUE))
                        .param("Search", "Shop1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetAllRatingsEnableWeekSearch() throws Exception {

        when(shopService.getAllShops(any(Pageable.class), eq(weekDayListString), eq(Boolean.TRUE), eq("Shop1")))
                .thenReturn(new PageImpl<>(List.of(sampleShopDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/shop/")
                        .param("Day of the Week", "MONDAY")
                        .param("Enable", String.valueOf(Boolean.TRUE))
                        .param("Search", "Shop1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetAllRatingsWeek() throws Exception {

        when(shopService.getAllShops(any(Pageable.class), eq(weekDayListString), eq(null), eq(null)))
                .thenReturn(new PageImpl<>(List.of(sampleShopDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/shop/")
                        .param("Day of the Week", "MONDAY"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetAllRatingsSearch() throws Exception {

        when(shopService.getAllShops(any(Pageable.class), eq(null), eq(null), eq("Shop1")))
                .thenReturn(new PageImpl<>(List.of(sampleShopDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/shop/")
                        .param("Search", "Shop1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetAllRatingsWeekSearch() throws Exception {

        when(shopService.getAllShops(any(Pageable.class), eq(weekDayListString), eq(null), eq("Shop1")))
                .thenReturn(new PageImpl<>(List.of(sampleShopDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/shop/")
                        .param("Day of the Week", "MONDAY")
                        .param("Search", "Shop1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetRatingById() throws Exception {

        when(shopService.getShopById(any(UUID.class))).thenReturn(sampleShopDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/shop/{id}", sampleShopDto.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(String.valueOf(sampleShopDto.getId())));
    }

    @Test
    public void testCreateRating() throws Exception {

        when(shopService.createShop(any(ShopDto.class))).thenReturn(sampleShopDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/shop/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleShopJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(String.valueOf(sampleShopDto.getDescription())));
    }

    @Test
    public void testUpdateRating() throws Exception {

        when(shopService.updateShop(any(UUID.class), any(ShopDto.class))).thenReturn(sampleShopDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/shop/{id}", sampleShopDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleShopJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(String.valueOf(sampleShopDto.getDescription())));
    }

    @Test
    public void testDeleteRating() throws Exception {

        doNothing().when(shopService).deleteShop(any(UUID.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/shop/{id}", sampleShopDto.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
