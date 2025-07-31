package com.kjm.toothlinedental.service;

import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import com.kjm.toothlinedental.dto.AuditLogFetchRequestDto;
import com.kjm.toothlinedental.dto.AuditLogResponseDto;
import org.springframework.stereotype.Service;

import com.kjm.toothlinedental.model.AuditLog;
import com.kjm.toothlinedental.repository.audit.AuditLogRepository;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void logAction(String action, String performedBy, String details) {
        AuditLog log = new AuditLog();
        log.setAction(action);
        log.setPerformedBy(performedBy);
        log.setTimestamp(LocalDateTime.now());
        log.setDetails(details);
        auditLogRepository.save(log);
    }

    public Page<AuditLogResponseDto> getAllLogs(AuditLogFetchRequestDto dto) {
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd hh:mm a");

        return auditLogRepository.findFilteredLogs(dto.getPerformedBy(), dto.getDate(), dto.getCategory(), pageable)
                .map(log -> new AuditLogResponseDto(
                        log.getId(),
                        log.getAction(),
                        log.getDetails(),
                        log.getPerformedBy(),
                        log.getTimestamp().format(formatter)
                ));
    }

    public List<AuditLogResponseDto> getLatestLogs() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd hh:mm a");
        return auditLogRepository.findTop5ByOrderByTimestampDesc()
                .stream()
                .map(log -> new AuditLogResponseDto(
                        log.getId(),
                        log.getAction(),
                        log.getDetails(),
                        log.getPerformedBy(),
                        log.getTimestamp().format(formatter)
                ))
                .toList();
    }
}