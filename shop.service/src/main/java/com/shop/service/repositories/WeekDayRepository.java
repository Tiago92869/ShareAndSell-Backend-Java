package com.shop.service.repositories;

import com.shop.service.domain.WeekDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WeekDayRepository extends JpaRepository<WeekDay, UUID> {
}
