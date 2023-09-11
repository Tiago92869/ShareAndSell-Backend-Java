package com.logs.service.service;

import com.logs.service.domain.Log;
import com.logs.service.dto.LogDto;
import com.logs.service.exceptions.EntityNotFoundException;
import com.logs.service.mapper.LogMapper;
import com.logs.service.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class LogService {

    private final LogRepository logRepository;

    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public List<LogDto> getAllLogs() {

        return this.logRepository.findAll().stream().map(LogMapper.INSTANCE::logToDto).toList();
    }

    public LogDto getLogById(UUID id) {

        Optional<Log> optionalLog = this.logRepository.findById(id);

        if(optionalLog.isEmpty()){
            throw new EntityNotFoundException("A Log with that id could not be found");
        }

        return LogMapper.INSTANCE.logToDto(optionalLog.get());
    }

    public LogDto createLog(LogDto logDto) {

        logDto.setId(UUID.randomUUID());

        Log log = LogMapper.INSTANCE.dtoToLog(logDto);

        return LogMapper.INSTANCE.logToDto(this.logRepository.save(log));
    }
}
