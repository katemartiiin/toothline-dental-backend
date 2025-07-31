package com.kjm.toothlinedental.dto.appointment;

import java.time.LocalDate;

public class AppointmentRequestDto {

    // Appointment info
    private LocalDate appointmentDate;
    private Long serviceId;
    private String patientName;

    // Paginate
    private int page = 0;
    private int size = 10;

    // Getters and setters
    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }

    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }
}
