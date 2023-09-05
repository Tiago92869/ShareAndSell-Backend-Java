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

    default byte[] toBytes(String string){
        return string != null ? string.getBytes() : null;
    }

    default String toString(byte[] bytes){
        return bytes != null ? Arrays.toString(bytes) : null;
    }
}
