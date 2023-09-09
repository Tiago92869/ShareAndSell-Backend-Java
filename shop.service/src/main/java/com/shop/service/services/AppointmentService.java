package com.shop.service.services;

import com.shop.service.domain.Appointment;
import com.shop.service.domain.Shop;
import com.shop.service.dto.AppointmentDto;
import com.shop.service.exceptions.EntityNotFoundException;
import com.shop.service.maps.AppointmentMapper;
import com.shop.service.repositories.AppointmentRepository;
import com.shop.service.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final ShopRepository shopRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, ShopRepository shopRepository) {
        this.appointmentRepository = appointmentRepository;
        this.shopRepository = shopRepository;
    }

    public Page<AppointmentDto> getAllAppointments(Pageable pageable, UUID shopId, UUID userId) {

        if(shopId != null && userId == null){

            return this.appointmentRepository.findByShopId(pageable, shopId).map(AppointmentMapper.INSTANCE::appointmentToDto);

        }else if(shopId == null && userId != null){

            return this.appointmentRepository.findByUserId(pageable, userId).map(AppointmentMapper.INSTANCE::appointmentToDto);

        }else if(shopId != null && userId != null){

            return this.appointmentRepository.findByShopIdAndUserId(pageable, shopId, userId).map(AppointmentMapper.INSTANCE::appointmentToDto);
        }

        return this.appointmentRepository.findAll(pageable).map(AppointmentMapper.INSTANCE::appointmentToDto);
    }

    public AppointmentDto getAppointmentById(UUID id) {

        Optional<Appointment> maybeAppointment = this.appointmentRepository.findById(id);

        if(maybeAppointment.isEmpty()){
            throw new EntityNotFoundException("A Appointment with that id does not exist");
        }

        return AppointmentMapper.INSTANCE.appointmentToDto(maybeAppointment.get());
    }

    public AppointmentDto createAppointment(AppointmentDto appointmentDto) {

        appointmentDto.setId(UUID.randomUUID());

        Optional<Shop> optionalShop = this.shopRepository.findById(appointmentDto.getShopId());

        if(optionalShop.isEmpty()){
            throw new EntityNotFoundException("A Shop with that id does not exist");
        }

        Appointment appointment = AppointmentMapper.INSTANCE.dtoToAppointment(appointmentDto);
        appointment.setShop(optionalShop.get());

        return AppointmentMapper.INSTANCE.appointmentToDto(this.appointmentRepository.save(appointment));
    }

    public AppointmentDto updateAppointment(UUID id, AppointmentDto appointmentDto) {

        Optional<Appointment> maybeAppointment = this.appointmentRepository.findById(id);

        if(maybeAppointment.isEmpty()){
            throw new EntityNotFoundException("A Appointment with that id does not exist");
        }

        Appointment appointment = maybeAppointment.get();

        if(appointmentDto.getDate() != null){
            appointment.setDate(appointmentDto.getDate());
        }

        if(appointmentDto.getTime() != null){
            appointment.setTime(appointmentDto.getTime());
        }

        if(appointmentDto.getUserId() != null){
            appointment.setUserId(appointmentDto.getUserId());
        }

        if(appointmentDto.getShopId() != null){

            Optional<Shop> optionalShop = this.shopRepository.findById(appointmentDto.getShopId());

            if(optionalShop.isEmpty()){
                throw new EntityNotFoundException("A Shop with that id does not exist");
            }

            appointment.setShop(optionalShop.get());
        }

        return AppointmentMapper.INSTANCE.appointmentToDto(this.appointmentRepository.save(appointment));
    }

    public void deleteAppointment(UUID id) {

        Optional<Appointment> maybeAppointment = this.appointmentRepository.findById(id);

        if(maybeAppointment.isEmpty()){
            throw new EntityNotFoundException("A Appointment with that id does not exist");
        }

        this.appointmentRepository.deleteById(id);
    }
}
