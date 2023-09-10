package com.shop.service.repositories;

import com.shop.service.domain.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RatingRepository extends JpaRepository<Rating, UUID> {
    Page<Rating> findByShopId(Pageable pageable, UUID shopId);

    Page<Rating> findByUserId(Pageable pageable, UUID userId);

    Page<Rating> findByShopIdAndUserId(Pageable pageable, UUID shopId, UUID userId);

    List<Rating> findByShopId(UUID shopId);
}
