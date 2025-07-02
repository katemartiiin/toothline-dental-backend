package com.kjm.toothlinedental.controller;

import com.kjm.toothlinedental.model.AuditLog;
import com.kjm.toothlinedental.service.AuditLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/security")
@PreAuthorize("hasRole('ADMIN')")
public class SecurityController {

    private final AuditLogService auditLogService;

    public SecurityController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    // Get all audit logs
    @GetMapping("/audit-logs")
    public ResponseEntity<List<AuditLog>> getAuditLogs() {
        return ResponseEntity.ok(auditLogService.getAllLogs());
    }

    // Manually create an audit log
    @PostMapping("/audit-logs")
    public ResponseEntity<String> createAuditLog(@RequestBody AuditLog log) {
        log.setTimestamp(LocalDateTime.now());
        auditLogService.logAction(log.getAction(), log.getPerformedBy(), log.getDetails());
        return ResponseEntity.ok("Audit log created.");
    }
}