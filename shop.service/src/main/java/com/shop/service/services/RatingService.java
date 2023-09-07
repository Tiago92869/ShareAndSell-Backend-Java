package com.shop.service.services;
import com.shop.service.domain.Rating;
import com.shop.service.dto.AppointmentDto;
import com.shop.service.dto.RatingDto;
import com.shop.service.exceptions.EntityNotFoundException;
import com.shop.service.maps.RatingMapper;
import com.shop.service.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Page<RatingDto> getAllRatings(Pageable pageable) {

        return this.ratingRepository.findAll(pageable).map(RatingMapper.INSTANCE::ratingToDto);
    }

    public RatingDto getRatingById(UUID id) {

        Optional<Rating> maybeRating = this.ratingRepository.findById(id);

        if(maybeRating.isEmpty()){
            throw new EntityNotFoundException("A Rating with that id does not exist");
        }

        return RatingMapper.INSTANCE.ratingToDto(maybeRating.get());
    }

    public RatingDto createRatting(RatingDto ratingDto) {

        ratingDto.setId(UUID.randomUUID());

        return RatingMapper.INSTANCE.ratingToDto(
                this.ratingRepository.save(RatingMapper.INSTANCE.dtoToRating(ratingDto)));
    }

    public RatingDto updateRating(UUID id, RatingDto ratingDto) {

        Optional<Rating> maybeRating = this.ratingRepository.findById(id);

        if(maybeRating.isEmpty()){
            throw new EntityNotFoundException("A Rating with that id does not exist");
        }

        Rating rating = maybeRating.get();

        if(ratingDto.getRate() != null){
            rating.setRate(ratingDto.getRate());
        }

        if(ratingDto.getDescription() != null){
            rating.setDescription(ratingDto.getDescription());
        }

        if(ratingDto.getDate() != null){
            rating.setDate(ratingDto.getDate());
        }

        if(ratingDto.getUserId() != null){
            rating.setUserId(ratingDto.getUserId());
        }

        return RatingMapper.INSTANCE.ratingToDto(this.ratingRepository.save(rating));
    }

    public void deleteRating(UUID id) {

        Optional<Rating> maybeRating = this.ratingRepository.findById(id);

        if(maybeRating.isEmpty()){
            throw new EntityNotFoundException("A Rating with that id does not exist");
        }

        this.ratingRepository.deleteById(id);
    }
}
