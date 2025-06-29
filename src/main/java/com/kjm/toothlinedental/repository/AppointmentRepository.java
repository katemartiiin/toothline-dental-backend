package com.kjm.toothlinedental.repository;

import com.kjm.toothlinedental.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
