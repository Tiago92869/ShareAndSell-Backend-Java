package com.shop.service.services;
import com.shop.service.dto.AppointmentDto;
import com.shop.service.dto.RatingDto;
import com.shop.service.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Page<AppointmentDto> getAllRatings(Pageable pageable) {

        return null;
    }

    public AppointmentDto getRatingById(UUID id) {

        return null;
    }

    public AppointmentDto createRatting(RatingDto ratingDto) {

        return null;
    }

    public AppointmentDto updateRating(UUID id, RatingDto ratingDto) {

        return null;
    }

    public void deleteRating(UUID id) {

    }
}
