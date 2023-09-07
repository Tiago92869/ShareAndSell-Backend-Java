package com.shop.service;

import com.shop.service.controllers.ProductController;
import com.shop.service.domain.Product;
import com.shop.service.dto.ProductDto;
import com.shop.service.maps.ProductMapper;
import com.shop.service.repositories.ProductRepository;
import com.shop.service.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ProductController.class)
@WithMockUser
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    Product product = new Product(UUID.fromString("0f3bb882-ec99-41d0-a0a2-c91508f455bb"), "apple", null, "image/png", "apple.png", null);

    ProductDto productDto = new ProductDto(UUID.fromString("0f3bb882-ec99-41d0-a0a2-c91508f455bb"), "apple");
    ProductDto updateProductDto = new ProductDto(UUID.fromString("0f3bb882-ec99-41d0-a0a2-c91508f455bb"), "apple");

    String exampleProductJson = "{\"id\": \"0f3bb882-ec99-41d0-a0a2-c91508f455bb\", \"description\": \"apple\"}";

    ProductDto createProduct = new ProductDto(null, "banana");
    ProductDto createProduct2 = new ProductDto(UUID.fromString("0f3bb882-ec99-41d0-a0a2-c91508f455bb"), "banana");
    String createProductJson = "{\"description\": \"banana\"}";

    String updateProductJson = "{\"description\": \"orange\"}";

    @Test
    public void testProductToDto(){

        ProductDto Dto = ProductMapper.INSTANCE.productToDto(product);

        assertNotNull(Dto);
        assertEquals(product.getDescription(), Dto.getDescription());
    }

    @Test
    public void testDtoToProduct(){

        Product pro = ProductMapper.INSTANCE.dtoToProduct(productDto);

        assertNotNull(pro);
        assertEquals(productDto.getDescription(), pro.getDescription());
    }

    @Test
    public void testGetAllProducts() throws Exception {
        //Given
        PageRequest pageRequest = PageRequest.of(0, 1);
        List<ProductDto> productList = new ArrayList<>();
        productList.add(productDto);
        Page<ProductDto> productPage = new PageImpl<>(productList, pageRequest, productList.size());

        Mockito.when(productService.getAllProducts(pageRequest)).thenReturn(productPage);

        //When
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product/?page=0&size=1").with(csrf())
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //Then
        MockMvcResultMatchers.status().isOk().match(result);

        // Assert content field only
        mockMvc.perform(requestBuilder)
                .andExpect(jsonPath("$.content[0].id").value(String.valueOf(product.getId())))
                .andExpect(jsonPath("$.content[0].description").value(product.getDescription()));
    }

    @Test
    public void testGetProductById() throws Exception {
        //Given
        Mockito.when(productService.getProductById(product.getId())).thenReturn(productDto);

        //When
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product/" + product.getId())
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //Then
        MockMvcResultMatchers.status().isOk().match(result);

        // Assert content field only
        mockMvc.perform(requestBuilder)
                .andExpect(jsonPath("id").value(String.valueOf(product.getId())))
                .andExpect(jsonPath("description").value(product.getDescription()));
    }

    @Test
    public void testPostProduct() throws Exception {
        // Given
        Mockito.when(productService.createProduct(createProduct)).thenReturn(createProduct2);

        // When
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/product/")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(createProductJson)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        // Then
        MockMvcResultMatchers.status().isOk().match(result);

        // Assert content field only within the same mockMvc.perform
        mockMvc.perform(requestBuilder)
                .andExpect(jsonPath("id").value(String.valueOf(createProduct2.getId())))
                .andExpect(jsonPath("description").value(createProduct2.getDescription()));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        // Given
        Mockito.when(productService.updateProduct(productDto.getId(), productDto)).thenReturn(productDto);

        // When
        RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/product/" + product.getId()).with(csrf()).param("action", "signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateProductJson)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        // Then
        MockMvcResultMatchers.status().isOk().match(result);

        // Assert content field only within the same mockMvc.perform
        mockMvc.perform(requestBuilder)
                .andExpect(jsonPath("id").value(String.valueOf(productDto.getId())))
                .andExpect(jsonPath("description").value("orange"));
    }

    @Test
    public void testDeleteProduct(){
        //When
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/product/" + product.getId());

        //Then
        Mockito.verify(productService, Mockito.times(1)).deleteProductById(product.getId());
    }
}
