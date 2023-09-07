package com.shop.service.controllers;

import com.shop.service.domain.Appointment;
import com.shop.service.dto.AppointmentDto;
import com.shop.service.services.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Appointment", description = "Appointments between users and shops")
@RequestMapping("/appointment")
@RestController
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping(value = "/")
    @Operation(summary = "List all appointments")
    @ResponseStatus(HttpStatus.OK)
    public Page<AppointmentDto> getAllAppointments(Pageable pageable){

        return this.appointmentService.getAllAppointments(pageable);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get Appointment by id")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentDto getAppointmentById(@PathVariable UUID id){

        return this.appointmentService.getAppointmentById(id);
    }

    @PostMapping(value = "/")
    @Operation(summary = "Create Appointment")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentDto createAppointment(@RequestBody AppointmentDto appointmentDto){

        return this.appointmentService.createAppointment(appointmentDto);
    }

    @PatchMapping(value = "/{id}")
    @Operation(summary = "Update Appointment by id")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentDto updateAppointment(@PathVariable UUID id, @RequestBody AppointmentDto appointmentDto){

        return this.appointmentService.updateAppointment(id, appointmentDto);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete Appointment by id")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAppointment(@PathVariable UUID id){

        this.appointmentService.deleteAppointment(id);
    }
}
