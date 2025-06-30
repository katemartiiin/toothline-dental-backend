package com.kjm.toothlinedental.mapper;

import com.kjm.toothlinedental.dto.AppointmentResponseDto;
import com.kjm.toothlinedental.model.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public AppointmentResponseDto toDto(Appointment appointment) {
        AppointmentResponseDto dto = new AppointmentResponseDto();
        dto.setDentistId(appointment.getDentist() != null ? appointment.getDentist().getId() : null);
        dto.setName(appointment.getPatient().getName());
        dto.setEmail(appointment.getPatient().getEmail());
        dto.setPhoneNumber(appointment.getPatient().getPhoneNumber());
        dto.setServiceId(appointment.getService().getId());
        dto.setServiceName(appointment.getService().getName());
        dto.setNotes(appointment.getNotes());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        return dto;
    }
}
