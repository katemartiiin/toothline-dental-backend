package com.kjm.toothlinedental.repository;

import com.kjm.toothlinedental.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>, AppointmentRepositoryCustom {

    List<Appointment> findAllByAppointmentDateAndArchivedFalse(LocalDate date);

    List<Appointment> findAllByArchivedTrue();
}
