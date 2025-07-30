package com.kjm.toothlinedental.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kjm.toothlinedental.model.AuditLog;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long>, AuditLogRepositoryCustom {
    List<AuditLog> findAllByOrderByTimestampDesc();

    List<AuditLog> findTop5ByOrderByTimestampDesc();
}