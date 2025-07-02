package com.kjm.toothlinedental.dto;

import com.kjm.toothlinedental.model.Appointment;

import java.util.List;

public class DashboardRequestDto {
    private List<Appointment> appointmentsToday;
    private long totalPatients;

    public List<Appointment> getAppointmentsToday() {
        return appointmentsToday;
    }

    public void setAppointmentsToday(List<Appointment> appointmentsToday) {
        this.appointmentsToday = appointmentsToday;
    }

    public long getTotalPatients() {
        return totalPatients;
    }

    public void setTotalPatients(long totalPatients) {
        this.totalPatients = totalPatients;
    }
}
