package com.shop.service.services;

import com.shop.service.domain.Product;
import com.shop.service.dto.ProductDto;
import com.shop.service.exceptions.BadRequestException;
import com.shop.service.exceptions.EntityNotFoundException;
import com.shop.service.exceptions.ValidationException;
import com.shop.service.maps.ProductMapper;
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

        if(productDto.getDescription() == null){
            throw new BadRequestException("The name and the file must be insert");
        }

        productDto.setId(UUID.randomUUID());
        Product product = ProductMapper.INSTANCE.dtoToProduct(productDto);

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

        return ProductMapper.INSTANCE.productToDto(this.productRepository.save(product));
    }


    public ResponseEntity<?> getProductImageById(UUID id) {

        Optional<Product> maybeOptional = this.productRepository.findById(id);

        if(maybeOptional.isEmpty()){
            throw new EntityNotFoundException("A Product with that id does not exist");
        }

        Product product = maybeOptional.get();

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

        this.productRepository.deleteById(id);
    }
}
