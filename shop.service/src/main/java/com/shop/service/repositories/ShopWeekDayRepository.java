package com.shop.service.repositories;

import com.shop.service.domain.ShopWeekDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShopWeekDayRepository extends JpaRepository<ShopWeekDay, UUID> {
}
