package com.kjm.toothlinedental.repository;

import java.util.List;
import java.time.LocalDate;

import com.kjm.toothlinedental.model.Appointment;

public interface AppointmentRepositoryCustom {
    List<Appointment> findFilteredAppointments(Long dentistId, String patientName, LocalDate appointmentDate);
}
