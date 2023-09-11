package com.logs.service.repository;

import com.logs.service.domain.Log;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LogRepository extends CassandraRepository<Log, UUID> {

    @AllowFiltering
    List<Log> findByUserId(UUID userId);
}
