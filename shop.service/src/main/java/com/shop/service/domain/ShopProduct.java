package com.shop.service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "shopProduct")
public class ShopProduct {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    private UUID id;

    @Column(name = "isEnable")
    private Boolean isEnable;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
