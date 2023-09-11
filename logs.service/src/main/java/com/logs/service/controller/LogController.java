package com.logs.service.controller;

import com.logs.service.domain.Log;
import com.logs.service.dto.LogDto;
import com.logs.service.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Tag(name = "Logs", description = "Manage Logs")
@RequestMapping("/log")
@RestController
public class LogController {

    private final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }


    @GetMapping("/")
    @Operation(summary = "Get all Logs")
    @ResponseStatus(HttpStatus.OK)
    public List<LogDto> getAllLogs(){

        return this.logService.getAllLogs();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Log by id")
    @ResponseStatus(HttpStatus.OK)
    public LogDto getLogById(@PathVariable UUID id){

        return this.logService.getLogById(id);
    }

    @PostMapping("/")
    @Operation(summary = "Create Log")
    @ResponseStatus(HttpStatus.OK)
    public LogDto createLog(@RequestBody LogDto logDto){

        return this.logService.createLog(logDto);
    }
}
