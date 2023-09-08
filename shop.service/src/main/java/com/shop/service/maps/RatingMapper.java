package com.shop.service.maps;

import com.shop.service.domain.Rating;
import com.shop.service.dto.RatingDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RatingMapper {

    RatingMapper INSTANCE = Mappers.getMapper(RatingMapper.class);

    RatingDto ratingToDto(Rating rating);

    Rating dtoToRating(RatingDto ratingDto);

}
