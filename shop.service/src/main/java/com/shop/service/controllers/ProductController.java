package com.shop.service.controllers;

import com.shop.service.domain.Product;
import com.shop.service.dto.ProductDto;
import com.shop.service.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @GetMapping(value = "/")
    @Operation(summary = "List all products")
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductDto> getAllProducts(Pageable pageable){

        return this.productService.getAllProducts(pageable);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "List product by id")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProductById(@PathVariable UUID id){

        return this.productService.getProductById(id);
    }

    @PostMapping(value = "/")
    @Operation(summary = "Create Product")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto createProduct(@RequestBody ProductDto productDto)
            throws IOException {

        return this.productService.createProduct(productDto);
    }

    @PatchMapping(value = "/{id}")
    @Operation(summary = "Update Product by id")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto updateProduct(@PathVariable UUID id, @RequestBody ProductDto productDto){

        return this.productService.updateProduct(id, productDto);
    }

    @GetMapping(value = "/image/{id}")
    @Operation(summary = "Get Product image by id")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getProductImageById(@PathVariable UUID id){

        return this.productService.getProductImageById(id);
    }

    @PatchMapping(value = "/image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload image to Product by id")
    @ResponseStatus(HttpStatus.OK)
    public void uploadImage(@PathVariable UUID id, @RequestParam("file") MultipartFile file){

        this.productService.uploadImage(id, file);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete product by id")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductById(@PathVariable UUID id){

        this.productService.deleteProductById(id);
    }
}
