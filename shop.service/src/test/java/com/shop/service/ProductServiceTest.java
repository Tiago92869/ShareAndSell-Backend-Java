package com.shop.service;

import com.shop.service.domain.Product;
import com.shop.service.dto.ProductDto;
import com.shop.service.repositories.ProductRepository;
import com.shop.service.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    Product product = new Product(UUID.fromString("0f3bb882-ec99-41d0-a0a2-c91508f455bb"), "apple", null, "image/png", "apple.png", null);

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        productService = new ProductService(productRepository);
    }

    @Test
    public void testGetAllProduct(){
        //Given
        PageRequest pageRequest = PageRequest.of(0, 1);
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Page<Product> weekDayPage = new PageImpl<>(productList, pageRequest, productList.size());

        Mockito.when(productRepository.findAll(pageRequest)).thenReturn(weekDayPage);

        // When
        Page<ProductDto> resultPage = productService.getAllProducts(pageRequest);

        // Then
        assertEquals(1, resultPage.getTotalElements());
        assertEquals(product.getDescription(), resultPage.getContent().get(0).getDescription());
    }

    @Test
    public void testGetProductById(){
        //Given
        UUID productId = UUID.fromString("0f3bb882-ec99-41d0-a0a2-c91508f455bb");
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.ofNullable(product));

        //When
        ProductDto productDto = productService.getProductById(productId);

        //Then
        Assertions.assertNotNull(productDto);
        assertEquals(product.getDescription(), productDto.getDescription());
    }

    @Test
    public void testCreateProduct(){
        //Given
        ProductDto productDto = new ProductDto(UUID.fromString("0f3bb882-ec99-41d0-a0a2-c91508f455bb"), "apple");

        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        //When
        ProductDto result = productService.createProduct(productDto);

        //Then
        assertNotNull(result);
        assertEquals(result.getDescription(), product.getDescription());

    }

    @Test
    public void testUpdateProduct(){}

    @Test
    public void testDeleteProduct(){}

}
