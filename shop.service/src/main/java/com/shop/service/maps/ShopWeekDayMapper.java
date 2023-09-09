package com.shop.service.maps;

import com.shop.service.domain.ShopWeekDay;
import com.shop.service.dto.ShopWeekDayDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShopWeekDayMapper {

    ShopWeekDayMapper INSTANCE = Mappers.getMapper(ShopWeekDayMapper.class);

    @Mapping(target = "shopId", source = "shop.id")
    @Mapping(target = "weekDayId", source = "weekDay.id")
    ShopWeekDayDto shopWeekDayToDto(ShopWeekDay shopWeekDay);

    ShopWeekDay dtoToShopWeekDay(ShopWeekDayDto shopWeekDayDto);
}
