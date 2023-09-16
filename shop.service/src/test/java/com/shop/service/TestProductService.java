package com.shop.service;

import com.shop.service.domain.Product;
import com.shop.service.dto.ProductDto;
import com.shop.service.repositories.ProductRepository;
import com.shop.service.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestProductService {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    private final Product sampleProduct1 = new Product(UUID.fromString("a9c6998f-e346-4fee-a451-8290bef086fd"),
            "Apple", new byte[111222333], "Fruit", "Apple-image", null);

    private final Product sampleProduct2 = new Product(UUID.fromString("c048d017-a4da-48a4-97d1-4527af863518"),
            "Potato", new byte[444555666], "Vegetal", "Potato-image", null);

    private final ProductDto sampleProductDto =
            new ProductDto(UUID.fromString("a9c6998f-e346-4fee-a451-8290bef086fd"), "Apple");

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository);
    }


    @Test
    public void testGetAllProducts(){

        when(productRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(sampleProduct1, sampleProduct2)));

        Page<ProductDto> result = productService.getAllProducts(PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    public void testGetProductById(){

        when(productRepository.findById(UUID.fromString("a9c6998f-e346-4fee-a451-8290bef086fd")))
                .thenReturn(Optional.of(sampleProduct1));

        ProductDto result = productService.getProductById(UUID.fromString("a9c6998f-e346-4fee-a451-8290bef086fd"));

        assertNotNull(result);
        assertEquals(result.getDescription(), sampleProduct1.getDescription());
    }

    @Test
    public void testCreateProduct(){

        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ProductDto result = productService.createProduct(sampleProductDto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(result.getDescription(), sampleProductDto.getDescription());
    }

    @Test
    public void testUpdateProduct(){

        when(productRepository.findById(sampleProductDto.getId())).thenReturn(Optional.of(sampleProduct1));

        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ProductDto result = productService.updateProduct(sampleProductDto.getId(), sampleProductDto);

        assertNotNull(result);
        assertEquals(result.getDescription(), sampleProductDto.getDescription());
    }

    @Test
    public void testDeleteProduct(){

        when(productRepository.findById(sampleProductDto.getId())).thenReturn(Optional.of(sampleProduct1));

        assertDoesNotThrow(() -> productService.deleteProductById(sampleProductDto.getId()));
    }
}
