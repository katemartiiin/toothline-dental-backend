package com.kjm.toothlinedental.repository;

import com.kjm.toothlinedental.model.AuditLog;

import java.util.List;

public interface AuditLogRepositoryCustom {
    List<AuditLog> findFilteredLogs(String performedBy, String date, String category);
}
