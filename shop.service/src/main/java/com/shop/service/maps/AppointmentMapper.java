package com.shop.service.maps;

import com.shop.service.domain.Appointment;
import com.shop.service.dto.AppointmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AppointmentMapper {

    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    AppointmentDto appointmentToDto(Appointment appointment);

    Appointment dtoToAppointment(AppointmentDto appointmentDto);

}
