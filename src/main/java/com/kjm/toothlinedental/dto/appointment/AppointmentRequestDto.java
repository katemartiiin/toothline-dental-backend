package com.kjm.toothlinedental.dto.appointment;

import java.time.LocalDate;

public class AppointmentRequestDto {

    // Appointment info
    private LocalDate appointmentDate;
    private Long dentistId;
    private String patientName;

    // Getters and setters
    public Long getDentistId() { return dentistId; }
    public void setDentistId(Long dentistId) { this.dentistId = dentistId; }

    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
}
