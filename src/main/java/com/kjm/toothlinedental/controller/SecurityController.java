package com.kjm.toothlinedental.controller;

import java.util.List;
import java.time.LocalDateTime;

import com.kjm.toothlinedental.dto.AuditLogFetchRequestDto;
import com.kjm.toothlinedental.dto.AuditLogResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import com.kjm.toothlinedental.model.AuditLog;
import com.kjm.toothlinedental.service.AuditLogService;

@RestController
@RequestMapping("/api/admin/security")
@PreAuthorize("hasRole('ADMIN')")
public class SecurityController {

    private final AuditLogService auditLogService;

    public SecurityController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    // Get all audit logs
    @PostMapping("/audit-logs")
    public ResponseEntity<List<AuditLogResponseDto>> getAuditLogs(@RequestBody AuditLogFetchRequestDto dto) {
        return ResponseEntity.ok(auditLogService.getAllLogs(dto));
    }
    // Get latest audit logs
    @GetMapping("/audit-logs/latest")
    public ResponseEntity<List<AuditLogResponseDto>> getLatestAuditLogs() {
        return ResponseEntity.ok(auditLogService.getLatestLogs());
    }
}