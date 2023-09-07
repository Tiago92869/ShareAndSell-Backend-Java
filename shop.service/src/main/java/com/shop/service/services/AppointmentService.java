package com.shop.service.services;

import com.shop.service.dto.AppointmentDto;
import com.shop.service.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Page<AppointmentDto> getAllAppointments(Pageable pageable) {

        return null;
    }

    public AppointmentDto getAppointmentById(UUID id) {

        return null;
    }

    public AppointmentDto createAppointment(AppointmentDto appointmentDto) {

        return null;
    }

    public AppointmentDto updateAppointment(UUID id, AppointmentDto appointmentDto) {

        return null;
    }

    public void deleteAppointment(UUID id) {


    }
}
