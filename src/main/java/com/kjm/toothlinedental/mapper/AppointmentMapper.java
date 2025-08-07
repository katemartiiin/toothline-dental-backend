package com.kjm.toothlinedental.mapper;

import org.springframework.stereotype.Component;

import com.kjm.toothlinedental.model.Appointment;
import com.kjm.toothlinedental.dto.appointment.AppointmentResponseDto;

@Component
public class AppointmentMapper {

    public AppointmentResponseDto toDto(Appointment appointment) {
        AppointmentResponseDto dto = new AppointmentResponseDto();
        dto.setId(appointment.getId());
        dto.setDentistId(appointment.getDentist() != null ? appointment.getDentist().getId() : null);
        dto.setName(appointment.getPatient().getName());
        dto.setEmail(appointment.getPatient().getEmail());
        dto.setPhoneNumber(appointment.getPatient().getPhoneNumber());
        dto.setServiceId(appointment.getService().getId());
        dto.setServiceName(appointment.getService().getName());
        dto.setNotes(appointment.getNotes());
        dto.setStatus(appointment.getStatus());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        dto.setDentistName(appointment.getDentist() != null ? appointment.getDentist().getName() : null);
        dto.setTreatmentPlan(appointment.getTreatmentPlan() != null ? appointment.getTreatmentPlan() : null);
        dto.setPaidAmount(appointment.getPaidAmount() != null ? appointment.getPaidAmount() : null);
        return dto;
    }
}
