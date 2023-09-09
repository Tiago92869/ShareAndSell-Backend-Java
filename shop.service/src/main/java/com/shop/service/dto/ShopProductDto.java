package com.shop.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShopProductDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private Boolean isEnable;

    private UUID shopId;

    private UUID productId;
}
