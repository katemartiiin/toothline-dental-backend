package com.kjm.toothlinedental.repository;

import java.util.List;
import java.util.Optional;

import com.kjm.toothlinedental.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kjm.toothlinedental.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);

    Optional<Patient> findByPhoneNumber(String phoneNumber);

    List<Patient> findAllByArchivedTrue();

    List<Patient> findByArchivedFalseOrderByCreatedAtDesc();

    List<Patient> findByNameContainingIgnoreCaseAndArchivedFalse(String name);
}
