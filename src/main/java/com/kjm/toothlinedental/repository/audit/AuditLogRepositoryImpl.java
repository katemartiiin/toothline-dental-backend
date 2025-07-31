package com.kjm.toothlinedental.repository.audit;

import com.kjm.toothlinedental.model.AuditLog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AuditLogRepositoryImpl implements AuditLogRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<AuditLog> findFilteredLogs(String performedBy, String date, String category, Pageable pageable) {
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
            LocalDateTime startOfDay = parsedDate.atStartOfDay();
            LocalDateTime endOfDay = parsedDate.atTime(23, 59, 59);
            predicates.add(cb.between(root.get("timestamp"), startOfDay, endOfDay));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));
        query.orderBy(cb.desc(root.get("timestamp")));

        // Create query and set pagination
        TypedQuery<AuditLog> typedQuery = entityManager.createQuery(query);
        int totalRows = typedQuery.getResultList().size();

        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<AuditLog> pagedResult = typedQuery.getResultList();

        return new PageImpl<>(pagedResult, pageable, totalRows);
    }
}
