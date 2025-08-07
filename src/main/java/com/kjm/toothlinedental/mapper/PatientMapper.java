package com.kjm.toothlinedental.mapper;

import org.springframework.stereotype.Component;

import com.kjm.toothlinedental.model.Patient;
import com.kjm.toothlinedental.dto.patient.PatientResponseDto;

@Component
public class PatientMapper {

    public PatientResponseDto toDto(Patient patient) {
        PatientResponseDto dto = new PatientResponseDto();
        dto.setId(patient.getId());
        dto.setName(patient.getName());
        dto.setEmail(patient.getEmail());
        dto.setPhoneNumber(patient.getPhoneNumber());
        return dto;
    }
}
