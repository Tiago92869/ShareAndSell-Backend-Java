package com.shop.service.services;

import com.shop.service.domain.Product;
import com.shop.service.dto.ProductDto;
import com.shop.service.exceptions.BadRequestException;
import com.shop.service.exceptions.EntityNotFoundException;
import com.shop.service.exceptions.ValidationException;
import com.shop.service.maps.ProductMapper;
import com.shop.service.rabbit.ProducerService;
import com.shop.service.repositories.ProductRepository;
import com.shop.service.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProducerService producerService;

    @Autowired
    public ProductService(ProductRepository productRepository, ProducerService producerService) {
        this.productRepository = productRepository;
        this.producerService = producerService;
    }

    public Page<ProductDto> getAllProducts(Pageable pageable, String search) {

        this.producerService.sendMessageLogService("Get all Products", "45fbf752-1e87-4086-93d3-44e637c26a96");

        if(search != null){

            return this.productRepository.findByDescriptionContainingIgnoreCase(pageable, search)
                    .map(ProductMapper.INSTANCE::productToDto);
        }

        return this.productRepository.findAll(pageable).map(ProductMapper.INSTANCE::productToDto);
    }


    public ProductDto getProductById(UUID id) {

        Optional<Product> maybeOptional = this.productRepository.findById(id);

        if(maybeOptional.isEmpty()){

            throw new EntityNotFoundException("A Product with that id does not exist");
        }

        this.producerService.sendMessageLogService("Get Product with Id " + id, "45fbf752-1e87-4086-93d3-44e637c26a96");
        return ProductMapper.INSTANCE.productToDto(maybeOptional.get());
    }


    public ProductDto createProduct(ProductDto productDto) {

        if(productDto.getDescription() == null){
            throw new BadRequestException("The name and the file must be insert");
        }

        productDto.setId(UUID.randomUUID());
        Product product = ProductMapper.INSTANCE.dtoToProduct(productDto);

        this.producerService.sendMessageLogService("Create Product with Id " + product.getId(), "45fbf752-1e87-4086-93d3-44e637c26a96");
        return ProductMapper.INSTANCE.productToDto(this.productRepository.save(product));
    }


    public ProductDto updateProduct(UUID id, ProductDto productDto) {

        Optional<Product> maybeOptional = this.productRepository.findById(id);

        if(maybeOptional.isEmpty()){

            throw new EntityNotFoundException("A Product with that id does not exist");
        }

        Product product = maybeOptional.get();

        if(productDto.getDescription() != null){

            product.setDescription(productDto.getDescription());
        }

        this.producerService.sendMessageLogService("Update Product with Id " + product.getId(), "45fbf752-1e87-4086-93d3-44e637c26a96");
        return ProductMapper.INSTANCE.productToDto(this.productRepository.save(product));
    }


    public ResponseEntity<?> getProductImageById(UUID id) {

        Optional<Product> maybeOptional = this.productRepository.findById(id);

        if(maybeOptional.isEmpty()){
            throw new EntityNotFoundException("A Product with that id does not exist");
        }

        Product product = maybeOptional.get();

        this.producerService.sendMessageLogService("Get Product Image with Id " + id, "45fbf752-1e87-4086-93d3-44e637c26a96");
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(product.getType()))
                .body(ImageUtil.decompressImage(maybeOptional.get().getPhoto()));
    }


    public void uploadImage(UUID id, MultipartFile file) {

        Optional<Product> maybeOptional = this.productRepository.findById(id);

        if(maybeOptional.isEmpty()){

            throw new EntityNotFoundException("A Product with that id does not exist");
        }

        try{
            Product product = maybeOptional.get();

            //check for content type of image
            if(file != null) {

               if(!Objects.equals(file.getContentType(), "image/png")
                       && !Objects.equals(file.getContentType(), "image/jpeg")){

                   throw new ValidationException("The image must be of type .png, .jpeg or .jpg");
               }
            }

            product.setType(file.getContentType());
            product.setName(file.getOriginalFilename());
            product.setPhoto(ImageUtil.compressImage(file.getBytes()));

            this.productRepository.save(product);
            this.producerService.sendMessageLogService("Upload Product Image with Id " + product.getId(), "45fbf752-1e87-4086-93d3-44e637c26a96");
        }
        catch (Exception e){
            throw new BadRequestException("Something went wrong when uploading the image " + e.getMessage());
        }
    }

    public void deleteProductById(UUID id) {

        Optional<Product> maybeOptional = this.productRepository.findById(id);

        if(maybeOptional.isEmpty()){

            throw new EntityNotFoundException("A Product with that id does not exist");
        }

        this.producerService.sendMessageLogService("Delete Product with Id " + id, "45fbf752-1e87-4086-93d3-44e637c26a96");
        this.productRepository.deleteById(id);
    }
}
