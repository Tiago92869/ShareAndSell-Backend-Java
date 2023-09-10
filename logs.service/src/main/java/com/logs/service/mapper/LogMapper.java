package com.logs.service.mapper;

import com.logs.service.domain.Log;
import com.logs.service.dto.LogDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LogMapper {

    LogMapper INSTANCE = Mappers.getMapper(LogMapper.class);

    LogDto logToDto(Log log);

    Log dtoToLog(LogDto logDto);
}
