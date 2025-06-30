package com.kjm.toothlinedental.repository;

import com.kjm.toothlinedental.model.Appointment;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepositoryCustom {
    List<Appointment> findFilteredAppointments(Long dentistId, String patientName, LocalDate appointmentDate);
}
