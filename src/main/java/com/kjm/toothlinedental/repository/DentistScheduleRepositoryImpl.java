package com.kjm.toothlinedental.repository;

import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaBuilder;

import com.kjm.toothlinedental.model.DentistSchedule;

@Repository
public class DentistScheduleRepositoryImpl implements DentistScheduleRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DentistSchedule> findFilteredDentistSchedules(Long dentistId, String schedDay) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<DentistSchedule> query = cb.createQuery(DentistSchedule.class);
        Root<DentistSchedule> root = query.from(DentistSchedule.class);

        List<Predicate> predicates = new ArrayList<>();

        if (dentistId != null) {
            predicates.add(cb.equal(root.get("dentist").get("id"), dentistId));
        }

        if (schedDay != null && !schedDay.isBlank()) {
            predicates.add(cb.equal(root.get("schedDay"), schedDay));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query).getResultList();
    }
}
