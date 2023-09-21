package com.shop.service;

import com.shop.service.controllers.AppointmentController;
import com.shop.service.domain.Time;
import com.shop.service.dto.AppointmentDto;
import com.shop.service.services.AppointmentService;
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
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@WebMvcTest(AppointmentController.class)
public class TestAppointmentController {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;

    private final AppointmentDto sampleAppointmentDto = new AppointmentDto(
            UUID.fromString("bdba9df0-d55b-4fa6-b58b-6638b9f9bb93"), LocalTime.now(), LocalDate.now(),
            UUID.fromString("42b3421a-aab6-4a45-92ea-ce556d2f5018"),
            UUID.fromString("f085f23a-d370-4ae8-9b14-74e8077df5ff"));

    private final AppointmentDto sampleAppointmentDto2 = new AppointmentDto(
            UUID.fromString("af22805e-6d91-42a5-b02d-68de0f38c228"), LocalTime.now(), LocalDate.now(),
            UUID.fromString("d991d27b-85b8-47ad-9e0b-921e72b35028"),
            UUID.fromString("a8ffa85f-14a2-4799-9b9d-3f55258a2b32"));

    private final String sampleAppointmentJson = "{\"time\": \"11:38:25\"," +
            "\"date\": \"2023-09-17\"," +
            "\"userId\": \"42b3421a-aab6-4a45-92ea-ce556d2f5018\"," +
            "\"shopId\": \"a8ffa85f-14a2-4799-9b9d-3f55258a2b32\"}";


    @Test
    public void testGetAllAppointments() throws Exception {

        when(appointmentService.getAllAppointments(any(Pageable.class), eq(null), eq(null), eq(null)))
                .thenReturn(new PageImpl<>(List.of(sampleAppointmentDto, sampleAppointmentDto2)));

        mockMvc.perform(MockMvcRequestBuilders.get("/appointment/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(2)));
    }

    @Test
    public void testGetAllAppointmentsShopId() throws Exception {

        when(appointmentService.getAllAppointments(any(Pageable.class), eq(sampleAppointmentDto.getShopId()), eq(null), eq(null)))
                .thenReturn(new PageImpl<>(List.of(sampleAppointmentDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/appointment/")
                .param("Shop Id", String.valueOf(sampleAppointmentDto.getShopId())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetAllAppointmentsUserId() throws Exception {

        when(appointmentService.getAllAppointments(any(Pageable.class), eq(null), eq(sampleAppointmentDto.getUserId()), eq(null)))
                .thenReturn(new PageImpl<>(List.of(sampleAppointmentDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/appointment/")
                .param("User Id", String.valueOf(sampleAppointmentDto.getUserId())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetAllAppointmentsShopIdUserId() throws Exception {

        when(appointmentService.getAllAppointments(any(Pageable.class), eq(sampleAppointmentDto.getShopId()), eq(sampleAppointmentDto.getUserId()), eq(null)))
                .thenReturn(new PageImpl<>(List.of(sampleAppointmentDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/appointment/")
                .param("Shop Id", String.valueOf(sampleAppointmentDto.getShopId()))
                .param("User Id", String.valueOf(sampleAppointmentDto.getUserId())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetAllAppointmentsPresent() throws Exception {

        when(appointmentService.getAllAppointments(any(Pageable.class), eq(null), eq(null), any(Time.class)))
                .thenReturn(new PageImpl<>(List.of(sampleAppointmentDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/appointment/")
                        .param("Time", String.valueOf(Time.PRESENT)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetAllAppointmentsShopIdPresent() throws Exception {

        when(appointmentService.getAllAppointments(any(Pageable.class), eq(sampleAppointmentDto.getShopId()), eq(null), any(Time.class)))
                .thenReturn(new PageImpl<>(List.of(sampleAppointmentDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/appointment/")
                        .param("Shop Id", String.valueOf(sampleAppointmentDto.getShopId()))
                        .param("Time", String.valueOf(Time.PRESENT)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetAllAppointmentsUserIdPresent() throws Exception {

        when(appointmentService.getAllAppointments(any(Pageable.class), eq(null), eq(sampleAppointmentDto.getUserId()), any(Time.class)))
                .thenReturn(new PageImpl<>(List.of(sampleAppointmentDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/appointment/")
                        .param("User Id", String.valueOf(sampleAppointmentDto.getUserId()))
                        .param("Time", String.valueOf(Time.PRESENT)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetAllAppointmentsShopIdUserIdPresent() throws Exception {

        when(appointmentService.getAllAppointments(any(Pageable.class), eq(sampleAppointmentDto.getShopId()), eq(sampleAppointmentDto.getUserId()), any(Time.class)))
                .thenReturn(new PageImpl<>(List.of(sampleAppointmentDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/appointment/")
                        .param("Shop Id", String.valueOf(sampleAppointmentDto.getShopId()))
                        .param("User Id", String.valueOf(sampleAppointmentDto.getUserId()))
                        .param("Time", String.valueOf(Time.PRESENT)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetAppointmentById() throws Exception {

        when(appointmentService.getAppointmentById(any(UUID.class))).thenReturn(sampleAppointmentDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/appointment/{id}", sampleAppointmentDto.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(String.valueOf(sampleAppointmentDto.getId())));
    }

    @Test
    public void testCreateAppointment() throws Exception {

        when(appointmentService.createAppointment(any(AppointmentDto.class))).thenReturn(sampleAppointmentDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/appointment/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleAppointmentJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(String.valueOf(sampleAppointmentDto.getUserId())));
    }

    @Test
    public void testUpdateAppointment() throws Exception {

        when(appointmentService.updateAppointment(any(UUID.class), any(AppointmentDto.class))).thenReturn(sampleAppointmentDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/appointment/{id}", sampleAppointmentDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleAppointmentJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(String.valueOf(sampleAppointmentDto.getUserId())));
    }

    @Test
    public void testDeleteAppointment() throws Exception {

        doNothing().when(appointmentService).deleteAppointment(any(UUID.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/appointment/{id}", sampleAppointmentDto.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
