package com.shop.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppointmentDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private LocalTime time;

    private LocalDate date;

    private UUID userId;

    private UUID shopId;
}
