package com.shop.service.repositories;

import com.shop.service.domain.ShopProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ShopProductRepository extends JpaRepository<ShopProduct, UUID> {
    Page<ShopProduct> findByShopId(Pageable pageable, UUID shopId);

    Page<ShopProduct> findByProductId(Pageable pageable, UUID productId);

    Page<ShopProduct> findByShopIdAndProductId(Pageable pageable, UUID shopId, UUID productId);

    Page<ShopProduct> findByShopIdAndIsEnable(Pageable pageable, UUID shopId, Boolean isEnable);

    Page<ShopProduct> findByProductIdAndIsEnable(Pageable pageable, UUID productId, Boolean isEnable);

    Page<ShopProduct> findByShopIdAndProductIdAndIsEnable(Pageable pageable, UUID shopId, UUID productId, Boolean isEnable);
}
