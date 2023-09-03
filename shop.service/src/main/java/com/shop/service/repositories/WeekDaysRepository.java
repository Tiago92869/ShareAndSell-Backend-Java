package com.shop.service.repositories;

import com.shop.service.domain.WeekDays;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WeekDaysRepository extends JpaRepository<WeekDays, UUID> {
}
