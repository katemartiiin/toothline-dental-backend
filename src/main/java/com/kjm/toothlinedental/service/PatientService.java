package com.kjm.toothlinedental.service;

import java.util.List;

import com.kjm.toothlinedental.dto.patient.PatientCreateRequestDto;
import com.kjm.toothlinedental.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kjm.toothlinedental.model.Patient;

import com.kjm.toothlinedental.common.ApiResponse;
import com.kjm.toothlinedental.common.SecurityUtils;

import com.kjm.toothlinedental.mapper.PatientMapper;

import com.kjm.toothlinedental.dto.patient.PatientRequestDto;
import com.kjm.toothlinedental.dto.patient.PatientResponseDto;

import com.kjm.toothlinedental.repository.PatientRepository;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final AuditLogService auditLogService;

    public PatientService (PatientRepository patientRepository,
                             PatientMapper patientMapper,
                           AuditLogService auditLogService
    ) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
        this.auditLogService = auditLogService;
    }

    /*
     * Fetch all patients
     * */
    public Page<PatientResponseDto> getAllPatients(String name, Pageable pageable) {
        Page<Patient> patients = (name == null || name.trim().isEmpty())
                ? patientRepository.findByArchivedFalseOrderByCreatedAtDesc(pageable)
                : patientRepository.findByNameContainingIgnoreCaseAndArchivedFalse(name, pageable);

        return patients.map(patientMapper::toDto);
    }

    /*
     * Fetch by patientId
     * */
    public PatientResponseDto getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + id));
        return patientMapper.toDto(patient);
    }

    /*
     * Fetch by email
     * */
    public PatientResponseDto getPatientByParams(PatientRequestDto dto) {

        Patient patient = new Patient();

        if (dto.getEmail() != null ) {
            patient = patientRepository.findByEmail(dto.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found with email: " + dto.getEmail()));
        }

        if (dto.getPhoneNumber() != null ) {
             patient = patientRepository.findByPhoneNumber(dto.getPhoneNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found with phone number: " + dto.getPhoneNumber()));
        }

        return patientMapper.toDto(patient);
    }

    /*
     * Create Patient
     * */
    public ApiResponse<PatientResponseDto> createPatient(PatientCreateRequestDto dto) {

        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setEmail(dto.getEmail());
        patient.setPhoneNumber(dto.getPhoneNumber());

        Patient saved = patientRepository.save(patient);
        PatientResponseDto responseDto = patientMapper.toDto(saved);

        String performedBy = SecurityUtils.getCurrentUsername();
        auditLogService.logAction("CREATE_PATIENT", performedBy, "Created patient #" + patient.getId());

        return new ApiResponse<>("Patient #" + patient.getId() +" created successfully", responseDto);
    }

    public ApiResponse<PatientResponseDto> updatePatient(Long id, PatientRequestDto dto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + id));

        if (dto.getName() != null) {
            patient.setName(dto.getName());
        }

        if (dto.getEmail() != null) {
            patient.setEmail(dto.getEmail());
        }

        if (dto.getPhoneNumber() != null) {
            patient.setPhoneNumber(dto.getPhoneNumber());
        }

        Patient saved = patientRepository.save(patient);

        String performedBy = SecurityUtils.getCurrentUsername();
        auditLogService.logAction("UPDATE_PATIENT", performedBy, "Updated patient #" + id);

        return new ApiResponse<>("Patient #" + id + " updated successfully", patientMapper.toDto(saved));
    }

    public void toggleArchivePatient(Long id, boolean isArchive) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + id));

        patient.setArchived(isArchive);
        patientRepository.save(patient);

        String performedBy = SecurityUtils.getCurrentUsername();
        String message = (isArchive ? "Archived" : "Restored") + " patient #" + id;
        auditLogService.logAction("ARCHIVE_PATIENT", performedBy, message);
    }

    public List<PatientResponseDto> getArchivedPatients() {
        List<Patient> archivedPatients = patientRepository.findAllByArchivedTrue();
        return archivedPatients.stream()
                .map(patientMapper::toDto)
                .toList();
    }
}
