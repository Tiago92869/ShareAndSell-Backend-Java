package com.shop.service;

import com.shop.service.controllers.RatingController;
import com.shop.service.dto.RatingDto;
import com.shop.service.services.RatingService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RatingController.class)
public class TestRatingController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    private final RatingDto sampleRatingDto = new RatingDto(UUID.fromString("89d3c769-8f45-41fb-b9dc-e62796f29ff1"),
            (float) 4.4, "Very good service", LocalDate.now(),
            UUID.fromString("6735c697-3a63-4c91-a164-680e98c9f5ea"),
            UUID.fromString("f085f23a-d370-4ae8-9b14-74e8077df5ff"));

    private final RatingDto sampleRatingDto2 = new RatingDto(UUID.fromString("89d3c769-8f45-41fb-b9dc-e62796f29ff1"),
            (float) 2.3, "Very good service", LocalDate.now(),
            UUID.fromString("d991d27b-85b8-47ad-9e0b-921e72b35028"),
            UUID.fromString("a8ffa85f-14a2-4799-9b9d-3f55258a2b32"));

    private final String sampleRatingJson = "{\"rate\": 4.4," +
            "\"description\": \"\"," +
            "\"date\": \"2023-09-17\"," +
            "\"userId\": \"6735c697-3a63-4c91-a164-680e98c9f5ea\"," +
            "\"shopId\": \"f085f23a-d370-4ae8-9b14-74e8077df5ff\"}";

    @Test
    public void testGetAllRatings() throws Exception {

        when(ratingService.getAllRatings(any(Pageable.class), eq(null), eq(null)))
                .thenReturn(new PageImpl<>(List.of(sampleRatingDto, sampleRatingDto2)));

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(2)));
    }

    @Test
    public void testGetAllRatingsShopId() throws Exception {

        when(ratingService.getAllRatings(any(Pageable.class), eq(sampleRatingDto.getShopId()), eq(null)))
                .thenReturn(new PageImpl<>(List.of(sampleRatingDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/")
                        .param("Shop Id", String.valueOf(sampleRatingDto.getShopId())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetAllRatingsUserId() throws Exception {

        when(ratingService.getAllRatings(any(Pageable.class), eq(null), eq(sampleRatingDto.getUserId())))
                .thenReturn(new PageImpl<>(List.of(sampleRatingDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/")
                        .param("User Id", String.valueOf(sampleRatingDto.getUserId())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetAllRatingsShopIdUserId() throws Exception {

        when(ratingService.getAllRatings(any(Pageable.class), eq(sampleRatingDto.getShopId()), eq(sampleRatingDto.getUserId())))
                .thenReturn(new PageImpl<>(List.of(sampleRatingDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/")
                        .param("Shop Id", String.valueOf(sampleRatingDto.getShopId()))
                        .param("User Id", String.valueOf(sampleRatingDto.getUserId())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetRatingById() throws Exception {

        when(ratingService.getRatingById(any(UUID.class))).thenReturn(sampleRatingDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/{id}", sampleRatingDto.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(String.valueOf(sampleRatingDto.getId())));
    }

    @Test
    public void testCreateRating() throws Exception {

        when(ratingService.createRatting(any(RatingDto.class))).thenReturn(sampleRatingDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/rating/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleRatingJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(String.valueOf(sampleRatingDto.getUserId())));
    }

    @Test
    public void testUpdateRating() throws Exception {

        when(ratingService.updateRating(any(UUID.class), any(RatingDto.class))).thenReturn(sampleRatingDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/rating/{id}", sampleRatingDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleRatingJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(String.valueOf(sampleRatingDto.getUserId())));
    }

    @Test
    public void testDeleteRating() throws Exception {

        doNothing().when(ratingService).deleteRating(any(UUID.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/rating/{id}", sampleRatingDto.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
