package com.kjm.toothlinedental.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kjm.toothlinedental.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);

    // (Optional) If you want to support search by phone number instead:
    Optional<Patient> findByPhoneNumber(String phoneNumber);

    List<Patient> findAllByArchivedTrue();
}
