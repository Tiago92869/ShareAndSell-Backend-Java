package com.shop.service.repositories;

import com.shop.service.domain.Shop;
import com.shop.service.domain.WeekDays;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ShopRepository extends JpaRepository<Shop, UUID> {
    Page<Shop> findByWeekDaysIn(Pageable pageable, List<WeekDays> weekDays);

    Page<Shop> findByIsEnable(Pageable pageable, Boolean isEnable);

    Page<Shop> findByWeekDaysInAndIsEnable(Pageable pageable, List<WeekDays> weekDays, Boolean isEnable);
}
