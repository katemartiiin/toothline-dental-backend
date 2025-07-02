package com.kjm.toothlinedental.dto.appointment;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentUpdateRequestDto {

    // Appointment info
    private String status;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    // Assigned dentist
    private Long dentistId;

    // Getters and setters
    public Long getDentistId() { return dentistId; }
    public void setDentistId(Long dentistId) { this.dentistId = dentistId; }

    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }

    public LocalTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
