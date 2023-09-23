package com.user.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private String email;

    private String fullName;

    private LocalDate birthdate;

    private String city;

    private String country;

    private String phoneNumber;

    private Boolean isEnable;

    private List<UUID> favorites;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
