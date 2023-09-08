package com.shop.service.maps;

import com.shop.service.domain.Rating;
import com.shop.service.dto.RatingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RatingMapper {

    RatingMapper INSTANCE = Mappers.getMapper(RatingMapper.class);

    @Mapping(target = "shopId", source = "shop.id")
    RatingDto ratingToDto(Rating rating);

    Rating dtoToRating(RatingDto ratingDto);

}
