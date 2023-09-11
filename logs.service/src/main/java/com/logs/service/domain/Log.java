package com.logs.service.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Log {

    @PrimaryKey
    private UUID id;
    private UUID userId;
    private LocalDateTime timeStamp;
    private String description;
}
