package com.shop.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    private String description;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String photo;
}
