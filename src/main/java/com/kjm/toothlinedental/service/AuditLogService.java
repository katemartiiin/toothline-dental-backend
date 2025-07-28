package com.kjm.toothlinedental.service;

import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.kjm.toothlinedental.dto.AuditLogResponseDto;
import org.springframework.stereotype.Service;

import com.kjm.toothlinedental.model.AuditLog;
import com.kjm.toothlinedental.repository.AuditLogRepository;

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

    public List<AuditLogResponseDto> getAllLogs() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd hh:mm a");

        return auditLogRepository.findAllByOrderByTimestampDesc()
                .stream()
                .map(log -> new AuditLogResponseDto(
                        log.getId(),
                        log.getDetails(),
                        log.getPerformedBy(),
                        log.getTimestamp().format(formatter).toLowerCase()
                ))
                .toList();
    }
}