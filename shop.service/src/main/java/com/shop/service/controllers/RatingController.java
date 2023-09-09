package com.shop.service.controllers;

import com.shop.service.dto.RatingDto;
import com.shop.service.services.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    public Page<RatingDto> getAllRatings(
            Pageable pageable,
            @Parameter(description = "Filter by shop id.")
            @RequestParam(value = "Shop Id", required = false) UUID shopId,
            @Parameter(description = "Filter by current user id.")
            @RequestParam(value = "User Id", required = false) UUID userId){

        return this.ratingService.getAllRatings(pageable, shopId, userId);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get Rating by id")
    @ResponseStatus(HttpStatus.OK)
    public RatingDto getRatingById(@PathVariable UUID id){

        return this.ratingService.getRatingById(id);
    }

    @PostMapping(value = "/")
    @Operation(summary = "Create Rating")
    @ResponseStatus(HttpStatus.OK)
    public RatingDto createRatting(@RequestBody RatingDto ratingDto){

        return this.ratingService.createRatting(ratingDto);
    }

    @PatchMapping(value = "/{id}")
    @Operation(summary = "Update Rating by id")
    @ResponseStatus(HttpStatus.OK)
    public RatingDto updateRating(@PathVariable UUID id, @RequestBody RatingDto ratingDto){

        return this.ratingService.updateRating(id, ratingDto);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete Rating by id")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRating(@PathVariable UUID id){

        this.ratingService.deleteRating(id);
    }
}
