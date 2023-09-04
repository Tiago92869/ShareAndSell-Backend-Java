package com.shop.service.controllers;

import com.shop.service.dto.ProductDto;
import com.shop.service.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Product", description = "Products that are available")
@RequestMapping("/product")
@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    @Operation(summary = "List all products")
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductDto> getAllProducts(Pageable pageable){

        return this.productService.getAllProducts(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "List product by id")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProductById(@PathVariable UUID id){

        return this.productService.getProductById(id);
    }

    @PostMapping("/")
    @Operation(summary = "Create Product")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto createProduct(@RequestBody ProductDto productDto){

        return this.productService.createProduct(productDto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update Product by id")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto updateProduct(@PathVariable UUID id, @RequestBody ProductDto productDto){

        return this.productService.updateProduct(id, productDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product by id")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductById(@PathVariable UUID id){

        this.productService.deleteProductById(id);
    }
}
