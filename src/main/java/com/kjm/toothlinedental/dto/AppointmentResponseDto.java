package com.kjm.toothlinedental.dto;

import com.kjm.toothlinedental.model.Appointment;
import com.kjm.toothlinedental.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppointmentResponseDto {

    // Patient info (used to find or create the patient)
    private String name;
    private String email;
    private String phoneNumber;

    // Appointment info
    private Long serviceId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String notes;

    // Optional for admin/staff
    private Long dentistId;
    private String serviceName;

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }

    public Long getDentistId() { return dentistId; }
    public void setDentistId(Long dentistId) { this.dentistId = dentistId; }

    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }

    public LocalTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
