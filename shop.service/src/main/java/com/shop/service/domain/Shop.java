package com.shop.service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "shop")
public class Shop {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "rate")
    private Float rate;

    @Column(name = "startTime")
    private LocalTime startTime;

    @Column(name = "endTime")
    private LocalTime endTime;

    @ManyToMany
    @Column(name = "weekDays")
    private List<WeekDays> weekDays;

    @Column(name = "isEnable")
    private Boolean isEnable;

    @OneToMany(mappedBy = "shop")
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "shop")
    private List<Rating> ratings;

    @OneToMany(mappedBy = "shop")
    private List<ShopProduct> shopProducts;
}