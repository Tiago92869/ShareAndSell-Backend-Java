package com.shop.service.controllers;

import com.shop.service.dto.AppointmentDto;
import com.shop.service.dto.RatingDto;
import com.shop.service.services.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Rating", description = "Ratings from users to shops")
@RequestMapping("/rating")
@RestController
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping(value = "/")
    @Operation(summary = "Get all Ratings")
    @ResponseStatus(HttpStatus.OK)
    public Page<AppointmentDto> getAllRatings(Pageable pageable){

        return this.ratingService.getAllRatings(pageable);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get Rating by id")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentDto getRatingById(@PathVariable UUID id){

        return this.ratingService.getRatingById(id);
    }

    @PostMapping(value = "/")
    @Operation(summary = "Create Rating")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentDto createRatting(@RequestBody RatingDto ratingDto){

        return this.ratingService.createRatting(ratingDto);
    }

    @PatchMapping(value = "/{id}")
    @Operation(summary = "Update Rating by id")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentDto updateRating(@PathVariable UUID id, @RequestBody RatingDto ratingDto){

        return this.ratingService.updateRating(id, ratingDto);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete Rating by id")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRating(@PathVariable UUID id){

        this.ratingService.deleteRating(id);
    }
}
