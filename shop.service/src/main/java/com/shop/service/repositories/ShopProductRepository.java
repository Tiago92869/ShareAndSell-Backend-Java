package com.shop.service.repositories;

import com.shop.service.domain.ShopProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShopProductRepository extends JpaRepository<ShopProduct, UUID> {
}
