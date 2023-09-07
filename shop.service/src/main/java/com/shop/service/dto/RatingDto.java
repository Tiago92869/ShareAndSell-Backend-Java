package com.shop.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RatingDto {

    private UUID id;

    private Float rate;

    private String description;

    private LocalDate date;

    private UUID userId;
}
