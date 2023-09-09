package com.shop.service.repositories;

import com.shop.service.domain.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    Page<Appointment> findByShopId(Pageable pageable, UUID shopId);

    Page<Appointment> findByUserId(Pageable pageable, UUID mine);

    Page<Appointment> findByShopIdAndUserId(Pageable pageable, UUID shopId, UUID mine);
}
