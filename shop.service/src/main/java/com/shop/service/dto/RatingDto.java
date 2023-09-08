package com.shop.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private Float rate;

    private String description;

    private LocalDate date;

    private UUID userId;

    private UUID shopId;
}
