package com.kjm.toothlinedental.repository;

import com.kjm.toothlinedental.model.AuditLog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AuditLogRepositoryImpl implements AuditLogRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AuditLog> findFilteredLogs(String performedBy, String date, String category) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AuditLog> query = cb.createQuery(AuditLog.class);
        Root<AuditLog> root = query.from(AuditLog.class);

        List<Predicate> predicates = new ArrayList<>();

        if (performedBy != null && !performedBy.isBlank()) {
            predicates.add(cb.equal(root.get("performedBy"), performedBy));
        }

        if (category != null && !category.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("action")), "%" + category.toLowerCase() + "%"));
        }

        if (date != null && !date.isBlank()) {
            LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);

            // start of day to end of day
            LocalDateTime startOfDay = parsedDate.atStartOfDay();
            LocalDateTime endOfDay = parsedDate.atTime(23, 59, 59);

            predicates.add(cb.between(root.get("timestamp"), startOfDay, endOfDay));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query).getResultList();
    }
}
