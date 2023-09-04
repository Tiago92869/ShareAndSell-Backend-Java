package com.shop.service.services;

import com.shop.service.dto.ProductDto;
import com.shop.service.maps.ProductMapper;
import com.shop.service.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductDto> getAllProducts(Pageable pageable) {

        return this.productRepository.findAll(pageable).map(ProductMapper.INSTANCE::productToDto);
    }

    public ProductDto getProductById(UUID id) {
        return null;
    }

    public ProductDto createProduct(ProductDto productDto) {
        return null;
    }

    public ProductDto updateProduct(UUID id, ProductDto productDto) {
        return null;
    }


    public void deleteProductById(UUID id) {
    }
}
