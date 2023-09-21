package com.shop.service.repositories;

import com.shop.service.domain.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    Page<Appointment> findByShopId(Pageable pageable, UUID shopId);

    Page<Appointment> findByUserId(Pageable pageable, UUID mine);

    Page<Appointment> findByShopIdAndUserId(Pageable pageable, UUID shopId, UUID mine);

    @Query("SELECT a FROM appointment a WHERE a.userId = :userId AND (a.date > current_date OR (a.date = current_date AND a.time > current_time))")
    List<Appointment> findByUserIdAndFutureDateTime(@Param("userId") UUID userId);

    @Query("SELECT a FROM appointment a WHERE a.date < current_date")
    Page<Appointment> findByPastDate(Pageable pageable);
    @Query("SELECT a FROM appointment a WHERE a.date < current_date AND a.userId = :userId")
    Page<Appointment> findByPastDateUser(Pageable pageable, @Param("userId") UUID userId);

    @Query("SELECT a FROM appointment a WHERE a.date < current_date AND a.shop.id = :shopId")
    Page<Appointment> findByPastDateShop(Pageable pageable, @Param("shopId") UUID shopId);

    @Query("SELECT a FROM appointment a WHERE a.date < current_date AND a.shop.id = :shopId AND a.userId = :userId")
    Page<Appointment> findByPastDateShopUser(
            Pageable pageable, @Param("shopId") UUID shopId, @Param("userId") UUID userId);

    @Query("SELECT a FROM appointment a WHERE a.date = current_date")
    Page<Appointment> findByPresentDate(Pageable pageable);

    @Query("SELECT a FROM appointment a WHERE a.date = current_date AND a.userId = :userId")
    Page<Appointment> findByPresentDateUser(Pageable pageable, @Param("userId") UUID userId);

    @Query("SELECT a FROM appointment a WHERE a.date = current_date AND a.shop.id = :shopId")
    Page<Appointment> findByPresentDateShop(Pageable pageable, @Param("shopId") UUID shopId);

    @Query("SELECT a FROM appointment a WHERE a.date = current_date AND a.shop.id = :shopId AND a.userId = :userId")
    Page<Appointment> findByPresentDateShopUser(
            Pageable pageable, @Param("shopId") UUID shopId, @Param("userId") UUID userId);

    @Query("SELECT a FROM appointment a WHERE a.date > current_date")
    Page<Appointment> findByFutureDate(Pageable pageable);

    @Query("SELECT a FROM appointment a WHERE a.date > current_date AND a.userId = :userId")
    Page<Appointment> findByFutureDateUser(Pageable pageable, @Param("userId") UUID userId);

    @Query("SELECT a FROM appointment a WHERE a.date > current_date AND a.shop.id = :shopId")
    Page<Appointment> findByFutureDateShop(Pageable pageable, @Param("shopId") UUID shopId);

    @Query("SELECT a FROM appointment a WHERE a.date > current_date AND a.shop.id = :shopId AND a.userId = :userId")
    Page<Appointment> findByFutureDateShopUser(
            Pageable pageable, @Param("shopId") UUID shopId, @Param("userId") UUID userId);


}
