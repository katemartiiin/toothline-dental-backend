package com.kjm.toothlinedental.repository.appointment;

import java.util.List;
import java.time.LocalDate;

import com.kjm.toothlinedental.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentRepositoryCustom {
    Page<Appointment> findFilteredAppointments(Long serviceId, String patientName, LocalDate appointmentDate, Long dentistId, Pageable pageable);
}
