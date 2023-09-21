package com.shop.service;

import com.shop.service.controllers.ProductController;
import com.shop.service.domain.Product;
import com.shop.service.dto.ProductDto;
import com.shop.service.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
public class TestProductController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private final ProductDto sampleProductDto =
            new ProductDto(UUID.fromString("a9c6998f-e346-4fee-a451-8290bef086fd"), "Apple");

    private final ProductDto sampleProductDto2 =
            new ProductDto(UUID.fromString("c048d017-a4da-48a4-97d1-4527af863518"), "Potato");

    private final String sampleProductJson = "{\"description\": \"Apple\"}";


    @Test
    public void testGetAllProducts() throws Exception {

        when(productService.getAllProducts(any(Pageable.class), eq(null)))
                .thenReturn(new PageImpl<>(List.of(sampleProductDto, sampleProductDto2)));

        mockMvc.perform(MockMvcRequestBuilders.get("/product/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(2)));
    }

    @Test
    public void testGetAllProductsSearch() throws Exception {

        when(productService.getAllProducts(any(Pageable.class), eq("apple")))
                .thenReturn(new PageImpl<>(List.of(sampleProductDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/product/")
                        .param("Search", "apple"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetProductById() throws Exception {

        when(productService.getProductById(any(UUID.class))).thenReturn(sampleProductDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/product/{id}", sampleProductDto.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(sampleProductDto.getDescription()));
    }

    @Test
    public void testCreateProduct() throws Exception {

        when(productService.createProduct(any(ProductDto.class))).thenReturn(sampleProductDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/product/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sampleProductJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(sampleProductDto.getDescription()));
    }

    @Test
    public void testUpdateProduct() throws Exception {

        when(productService.updateProduct(any(UUID.class), any(ProductDto.class))).thenReturn(sampleProductDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/product/{id}", sampleProductDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(sampleProductJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(sampleProductDto.getDescription()));
    }

    @Test
    public void testDeleteProduct() throws Exception {

        doNothing().when(productService).deleteProductById(any(UUID.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/product/{id}", sampleProductDto.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
