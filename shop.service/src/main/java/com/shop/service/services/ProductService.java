package com.shop.service.services;

import com.shop.service.domain.Product;
import com.shop.service.dto.ProductDto;
import com.shop.service.exceptions.EntityNotFoundException;
import com.shop.service.maps.ProductMapper;
import com.shop.service.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
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

        Optional<Product> maybeOptional = this.productRepository.findById(id);

        if(maybeOptional.isEmpty()){

            throw new EntityNotFoundException("A Product with that id does not exist");
        }

        return ProductMapper.INSTANCE.productToDto(maybeOptional.get());
    }

    public ProductDto createProduct(ProductDto productDto) {

        productDto.setId(UUID.randomUUID());

        Product product = this.productRepository.save(ProductMapper.INSTANCE.dtoToProduct(productDto));

        return ProductMapper.INSTANCE.productToDto(product);
    }

    public ProductDto updateProduct(UUID id, ProductDto productDto) {

        Optional<Product> maybeOptional = this.productRepository.findById(id);

        if(maybeOptional.isEmpty()){

            throw new EntityNotFoundException("A Product with that id does not exist");
        }

        Product product = this.productRepository.save(ProductMapper.INSTANCE.dtoToProduct(productDto));

        return ProductMapper.INSTANCE.productToDto(product);
    }


    public void deleteProductById(UUID id) {

        Optional<Product> maybeOptional = this.productRepository.findById(id);

        if(maybeOptional.isEmpty()){

            throw new EntityNotFoundException("A Product with that id does not exist");
        }

        this.productRepository.delete(maybeOptional.get());
    }
}
