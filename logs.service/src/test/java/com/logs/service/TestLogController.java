package com.logs.service;

import com.logs.service.controller.LogController;
import com.logs.service.dto.LogDto;
import com.logs.service.service.LogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LogController.class)
public class TestLogController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LogService logService;

    private final LogDto sampleLogDto = new LogDto(UUID.fromString("f7a2ed6e-0aad-42ee-8c02-b8c383c71328"),
            UUID.fromString("586ed6b2-7a6e-497e-8a59-6ef2117bb679"), LocalDateTime.now(), "Teste 1");

    private final LogDto sampleLogDto2 = new LogDto(UUID.fromString("6e668acb-4884-47be-a091-806dd5a32bac"),
            UUID.fromString("058c00b3-9b9f-4bbb-9668-b928400fa60c"), LocalDateTime.now(), "Teste 1");

    private final String sampleProductJson = "{\"userId\": \"586ed6b2-7a6e-497e-8a59-6ef2117bb679\"," +
            "\"timeStamp\": \"2023-09-17T19:53:11.255Z\"," +
            "\"description\": \"Teste 1\"}";


    @Test
    public void testGetAllProducts() throws Exception {

        when(logService.getAllLogs(eq(null)))
                .thenReturn(new ArrayList<>(List.of(sampleLogDto, sampleLogDto2)));

        mockMvc.perform(MockMvcRequestBuilders.get("/log/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetAllProductsUserId() throws Exception {

        when(logService.getAllLogs(any(UUID.class)))
                .thenReturn(new ArrayList<>(List.of(sampleLogDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/log/")
                        .param("User Id", "586ed6b2-7a6e-497e-8a59-6ef2117bb679"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }

    @Test
    public void testGetProductById() throws Exception {

        when(logService.getLogById(any(UUID.class))).thenReturn(sampleLogDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/log/{id}", sampleLogDto.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(sampleLogDto.getDescription()));
    }

    @Test
    public void testCreateProduct() throws Exception {

        when(logService.createLog(any(LogDto.class))).thenReturn(sampleLogDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/log/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleProductJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(sampleLogDto.getDescription()));
    }
}
