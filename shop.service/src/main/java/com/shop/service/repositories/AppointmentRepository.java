package com.shop.service.repositories;

import com.shop.service.domain.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    Page<Appointment> findByShopId(Pageable pageable, UUID shopId);

    Page<Appointment> findByUserId(Pageable pageable, UUID mine);

    Page<Appointment> findByShopIdAndUserId(Pageable pageable, UUID shopId, UUID mine);

    @Query("SELECT a FROM appointment a WHERE a.userId = :userId AND (a.date > current_date OR (a.date = current_date AND a.time > current_time))")
    List<Appointment> findByUserIdAndFutureDateTime(@Param("userId") UUID userId);

}
