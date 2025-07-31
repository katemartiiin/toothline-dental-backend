package com.kjm.toothlinedental.repository.audit;

import com.kjm.toothlinedental.model.AuditLog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuditLogRepositoryCustom {
    Page<AuditLog> findFilteredLogs(String performedBy, String date, String category, Pageable pageable);
}
