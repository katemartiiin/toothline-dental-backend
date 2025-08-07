package com.kjm.toothlinedental.repository.appointment;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

import com.kjm.toothlinedental.model.AuditLog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.kjm.toothlinedental.model.Appointment;

@Repository
public class AppointmentRepositoryImpl implements AppointmentRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Appointment> findFilteredAppointments(Long serviceId, String patientName, LocalDate appointmentDate, Long dentistId, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Appointment> query = cb.createQuery(Appointment.class);
        Root<Appointment> root = query.from(Appointment.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.isFalse(root.get("archived")));

        if (dentistId != null) {
            predicates.add(cb.equal(root.get("dentist").get("id"), dentistId));
        }

        if (serviceId != null) {
            predicates.add(cb.equal(root.get("service").get("id"), serviceId));
        }

        if (patientName != null && !patientName.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("patient").get("name")), "%" + patientName.toLowerCase() + "%"));
        }

        if (appointmentDate != null) {
            predicates.add(cb.equal(root.get("appointmentDate"), appointmentDate));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));
        query.orderBy(cb.desc(root.get("createdAt")));
        // Create query and set pagination
        TypedQuery<Appointment> typedQuery = entityManager.createQuery(query);
        int totalRows = typedQuery.getResultList().size();

        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<Appointment> pagedResult = typedQuery.getResultList();

        return new PageImpl<>(pagedResult, pageable, totalRows);
    }
}
