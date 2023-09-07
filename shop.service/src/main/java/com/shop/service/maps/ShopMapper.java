package com.shop.service.maps;

import com.shop.service.domain.Shop;
import com.shop.service.dto.ShopDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShopMapper {

    ShopMapper INSTANCE = Mappers.getMapper(ShopMapper.class);

    ShopDto shopToDto(Shop shop);

    Shop dtoToShop(ShopDto shopDto);

}
