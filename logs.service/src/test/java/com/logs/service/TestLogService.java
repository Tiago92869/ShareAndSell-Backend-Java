package com.logs.service;

import com.logs.service.domain.Log;
import com.logs.service.dto.LogDto;
import com.logs.service.mapper.LogMapper;
import com.logs.service.repository.LogRepository;
import com.logs.service.service.LogService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TestLogService {

    @Mock
    private LogRepository logRepository;

    private LogService logService;

    private final Log sampleLog = new Log(UUID.fromString("f7a2ed6e-0aad-42ee-8c02-b8c383c71328"),
            UUID.fromString("586ed6b2-7a6e-497e-8a59-6ef2117bb679"), LocalDateTime.now(), "Teste 1");

    private final Log sampleLog2 = new Log(UUID.fromString("6e668acb-4884-47be-a091-806dd5a32bac"),
            UUID.fromString("058c00b3-9b9f-4bbb-9668-b928400fa60c"), LocalDateTime.now(), "Teste 2");

    private final LogDto sampleLogDto = new LogDto(UUID.fromString("f7a2ed6e-0aad-42ee-8c02-b8c383c71328"),
            UUID.fromString("586ed6b2-7a6e-497e-8a59-6ef2117bb679"), LocalDateTime.now(), "Teste 1");

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        logService = new LogService(logRepository);
    }

    @Test
    public void testLogToDto(){

        LogDto result = LogMapper.INSTANCE.logToDto(sampleLog);

        assertNotNull(result);
        assertEquals(result.getId(), sampleLog.getId());
        assertEquals(result.getUserId(), sampleLog.getUserId());
    }

    @Test
    public void testDtoToLog(){

        Log result = LogMapper.INSTANCE.dtoToLog(sampleLogDto);

        assertNotNull(result);
        assertEquals(result.getId(), sampleLogDto.getId());
        assertEquals(result.getUserId(), sampleLogDto.getUserId());
    }

    @Test
    public void testGetAllLogs(){

        when(logRepository.findAll()).thenReturn(new ArrayList<>(List.of(sampleLog, sampleLog2)));

        List<LogDto> result = logService.getAllLogs(null);

        Assertions.assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetAllLogsUserId(){

        when(logRepository.findByUserId(any(UUID.class))).thenReturn(new ArrayList<>(List.of(sampleLog)));


        List<LogDto> result = logService.getAllLogs(UUID.fromString("586ed6b2-7a6e-497e-8a59-6ef2117bb679"));

        Assertions.assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testGetLogById(){

        when(logRepository.findById(UUID.fromString("a9c6998f-e346-4fee-a451-8290bef086fd")))
                .thenReturn(Optional.of(sampleLog));

        LogDto result = logService.getLogById(UUID.fromString("a9c6998f-e346-4fee-a451-8290bef086fd"));

        Assertions.assertNotNull(result);
        assertEquals(result.getDescription(), sampleLog.getDescription());
    }

    @Test
    public void createLog(){

        when(logRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        LogDto result = logService.createLog(sampleLogDto);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getId());
        assertEquals(result.getDescription(), sampleLogDto.getDescription());
    }
}
