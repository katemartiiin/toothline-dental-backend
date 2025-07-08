package com.kjm.toothlinedental.repository;

import java.util.List;
import java.time.LocalDate;

import com.kjm.toothlinedental.model.Appointment;

public interface AppointmentRepositoryCustom {
    List<Appointment> findFilteredAppointments(Long serviceId, String patientName, LocalDate appointmentDate, Long dentistId);
}
