package com.kjm.toothlinedental.repository.appointment;

import java.util.List;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kjm.toothlinedental.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>, AppointmentRepositoryCustom {

    List<Appointment> findAllByAppointmentDateAndArchivedFalse(LocalDate date);

    List<Appointment> findAllByArchivedTrue();
}
