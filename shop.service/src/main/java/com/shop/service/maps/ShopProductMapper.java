package com.shop.service.maps;

import com.shop.service.domain.ShopProduct;
import com.shop.service.dto.ShopProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShopProductMapper {

    ShopProductMapper INSTANCE = Mappers.getMapper(ShopProductMapper.class);

    @Mapping(target = "shopId", source = "shop.id")
    @Mapping(target = "productId", source = "product.id")
    ShopProductDto shopProductToDto(ShopProduct shopProduct);

    ShopProduct dtoToShopProduct(ShopProductDto shopProductDto);

}
