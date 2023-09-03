package com.shop.service.maps;

import com.shop.service.domain.WeekDay;
import com.shop.service.dto.WeekDayDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WeekDayMapper {

    WeekDayMapper INSTANCE = Mappers.getMapper( WeekDayMapper.class);

    WeekDayDto weekDaysToDto(WeekDay weekDay);

}

