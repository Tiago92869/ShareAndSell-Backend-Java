package com.logs.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    private UUID userID;
    private LocalDateTime timeStamp;
    private String description;
}
