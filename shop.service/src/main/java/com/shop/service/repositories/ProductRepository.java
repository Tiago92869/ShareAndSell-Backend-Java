package com.shop.service.repositories;

import com.shop.service.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    Page<Product> findByDescriptionContainingIgnoreCase(Pageable pageable, String search);
}
