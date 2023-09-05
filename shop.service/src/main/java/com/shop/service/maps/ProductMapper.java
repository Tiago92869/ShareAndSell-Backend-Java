package com.shop.service.maps;

import com.shop.service.domain.Product;
import com.shop.service.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDto productToDto(Product product);

    Product dtoToProduct(ProductDto productDto);
}
