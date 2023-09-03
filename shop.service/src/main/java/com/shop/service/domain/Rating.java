package com.shop.service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ratting")
public class Rating {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    private UUID id;

    @Column(name = "rate")
    private Float rate;

    @Column(name = "description")
    private String description;

    @Column(name = "createdDate")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;
}
