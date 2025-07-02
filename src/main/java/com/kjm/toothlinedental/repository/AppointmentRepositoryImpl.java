package com.kjm.toothlinedental.repository;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Repository;

import com.kjm.toothlinedental.model.Appointment;

@Repository
public class AppointmentRepositoryImpl implements AppointmentRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Appointment> findFilteredAppointments(Long dentistId, String patientName, LocalDate appointmentDate) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Appointment> query = cb.createQuery(Appointment.class);
        Root<Appointment> root = query.from(Appointment.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.isFalse(root.get("archived")));

        if (dentistId != null) {
            predicates.add(cb.equal(root.get("dentist").get("id"), dentistId));
        }

        if (patientName != null && !patientName.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("patient").get("name")), "%" + patientName.toLowerCase() + "%"));
        }

        if (appointmentDate != null) {
            predicates.add(cb.equal(root.get("appointmentDate"), appointmentDate));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query).getResultList();
    }
}
