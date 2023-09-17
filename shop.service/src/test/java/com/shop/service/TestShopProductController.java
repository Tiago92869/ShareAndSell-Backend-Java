package com.shop.service;


import com.shop.service.controllers.ShopProductController;
import com.shop.service.dto.ShopProductDto;
import com.shop.service.services.ShopProductService;
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
@WebMvcTest(ShopProductController.class)
public class TestShopProductController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopProductService shopProductService;

    private final ShopProductDto sampleShopProductDto = new ShopProductDto(
            UUID.fromString("31a78a1a-0863-4cbc-966e-f1faf95b6e41"), true,
            UUID.fromString("f085f23a-d370-4ae8-9b14-74e8077df5ff"),
            UUID.fromString("a9c6998f-e346-4fee-a451-8290bef086fd"));

    private final ShopProductDto sampleShopProductDto2 = new ShopProductDto(
            UUID.fromString("a31e069d-ae35-427b-a93d-3bfea56cde9e"), false,
            UUID.fromString("a31e069d-ae35-427b-a93d-3bfea56cde9e"),
            UUID.fromString("b471e5ee-55ee-489d-a9ad-e7d63722bf19"));

    private final String sampleShopProductJson = "{\"isEnable\": true," +
            "\"shopId\": \"6735c697-3a63-4c91-a164-680e98c9f5ea\"," +
            "\"productId\": \"f085f23a-d370-4ae8-9b14-74e8077df5ff\"}";

    @Test
    public void testGetAllShopProducts() throws Exception {

        when(shopProductService.getAllShopProducts(any(Pageable.class), eq(null), eq(null), eq(null)))
                .thenReturn(new PageImpl<>(List.of(sampleShopProductDto, sampleShopProductDto2)));

        mockMvc.perform(MockMvcRequestBuilders.get("/shop-product/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(2)));
    }

    @Test
    public void testGetAllShopProductsShopIdTrue() throws Exception {

        when(shopProductService.getAllShopProducts(any(Pageable.class), eq(sampleShopProductDto.getShopId()), eq(null), eq(true)))
                .thenReturn(new PageImpl<>(List.of(sampleShopProductDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/shop-product/")
                        .param("Shop Id", String.valueOf(sampleShopProductDto.getShopId()))
                        .param("Enable", String.valueOf(Boolean.TRUE)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetAllShopProductsProductIdTrue() throws Exception {

        when(shopProductService.getAllShopProducts(any(Pageable.class), eq(null), eq(sampleShopProductDto.getProductId()), eq(true)))
                .thenReturn(new PageImpl<>(List.of(sampleShopProductDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/shop-product/")
                        .param("Product Id", String.valueOf(sampleShopProductDto.getProductId()))
                        .param("Enable", String.valueOf(Boolean.TRUE)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetAllShopProductsShopIdProductIdTrue() throws Exception {

        when(shopProductService.getAllShopProducts(any(Pageable.class), eq(sampleShopProductDto.getShopId()),
                eq(sampleShopProductDto.getProductId()), eq(true))).thenReturn(new PageImpl<>(List.of(sampleShopProductDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/shop-product/")
                        .param("Shop Id", String.valueOf(sampleShopProductDto.getShopId()))
                        .param("Product Id", String.valueOf(sampleShopProductDto.getProductId()))
                        .param("Enable", String.valueOf(Boolean.TRUE)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetAllShopProductsShopIdFalse() throws Exception {

        when(shopProductService.getAllShopProducts(any(Pageable.class), eq(sampleShopProductDto2.getShopId()),
                eq(null), eq(false))).thenReturn(new PageImpl<>(List.of(sampleShopProductDto2)));

        mockMvc.perform(MockMvcRequestBuilders.get("/shop-product/")
                        .param("Shop Id", String.valueOf(sampleShopProductDto2.getShopId()))
                        .param("Enable", String.valueOf(Boolean.FALSE)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetAllShopProductsProductIdFalse() throws Exception {

        when(shopProductService.getAllShopProducts(any(Pageable.class), eq(null),
                eq(sampleShopProductDto2.getProductId()), eq(false)))
                .thenReturn(new PageImpl<>(List.of(sampleShopProductDto2)));

        mockMvc.perform(MockMvcRequestBuilders.get("/shop-product/")
                        .param("Product Id", String.valueOf(sampleShopProductDto2.getProductId()))
                        .param("Enable", String.valueOf(Boolean.FALSE)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetAllShopProductsShopIdProductIdFalse() throws Exception {

        when(shopProductService.getAllShopProducts(any(Pageable.class), eq(sampleShopProductDto2.getShopId()),
                eq(sampleShopProductDto2.getProductId()), eq(false))).thenReturn(new PageImpl<>(List.of(sampleShopProductDto2)));

        mockMvc.perform(MockMvcRequestBuilders.get("/shop-product/")
                        .param("Shop Id", String.valueOf(sampleShopProductDto2.getShopId()))
                        .param("Product Id", String.valueOf(sampleShopProductDto2.getProductId()))
                        .param("Enable", String.valueOf(Boolean.FALSE)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetShopProductById() throws Exception {

        when(shopProductService.getShopProductById(any(UUID.class))).thenReturn(sampleShopProductDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/shop-product/{id}", sampleShopProductDto.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(String.valueOf(sampleShopProductDto.getId())));
    }

    @Test
    public void testCreateShopProduct() throws Exception {

        when(shopProductService.createShopProduct(any(ShopProductDto.class))).thenReturn(sampleShopProductDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/shop-product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleShopProductJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(String.valueOf(sampleShopProductDto.getProductId())));
    }

    @Test
    public void testUpdateShopProduct() throws Exception {

        when(shopProductService.updateShopProducts(any(UUID.class), any(ShopProductDto.class))).thenReturn(sampleShopProductDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/shop-product/{id}", sampleShopProductDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleShopProductJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(String.valueOf(sampleShopProductDto.getProductId())));
    }

    @Test
    public void testDeleteShopProduct() throws Exception {

        doNothing().when(shopProductService).deleteShopProducts(any(UUID.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/shop-product/{id}", sampleShopProductDto.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
