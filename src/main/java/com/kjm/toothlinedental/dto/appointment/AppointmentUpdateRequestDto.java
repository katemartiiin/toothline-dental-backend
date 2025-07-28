package com.kjm.toothlinedental.dto.appointment;

import com.kjm.toothlinedental.model.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentUpdateRequestDto {

    // Appointment info
    private AppointmentStatus status;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String notes;

    // Assigned dentist
    private Long dentistId;
    // Service
    private Long serviceId;

    // Getters and setters
    public Long getDentistId() { return dentistId; }
    public void setDentistId(Long dentistId) { this.dentistId = dentistId; }

    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }

    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }

    public LocalTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; }

    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

}
