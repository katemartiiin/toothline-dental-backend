package com.kjm.toothlinedental.controller;

import java.util.List;

import com.kjm.toothlinedental.dto.patient.PatientCreateRequestDto;
import com.kjm.toothlinedental.dto.patient.PatientNameRequestDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.kjm.toothlinedental.common.ApiResponse;
import com.kjm.toothlinedental.dto.patient.PatientRequestDto;
import com.kjm.toothlinedental.dto.patient.PatientResponseDto;
import com.kjm.toothlinedental.service.PatientService;

@RestController
@RequestMapping("/api/admin/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController (PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/{id}/view")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST')")
    public ResponseEntity<ApiResponse<PatientResponseDto>> getPatientById(@PathVariable Long id) {
        var data = patientService.getPatientById(id);
        return ResponseEntity.ok(new ApiResponse<>("Patient fetched successfully", data));
    }

    @GetMapping("/archived")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST')")
    public ResponseEntity<ApiResponse<List<PatientResponseDto>>> getArchivedPatients() {
        List<PatientResponseDto> data = patientService.getArchivedPatients();
        return ResponseEntity.ok(new ApiResponse<>("Archived patients fetched successfully", data));
    }

    @PostMapping("/fetch-all")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST')")
    public ResponseEntity<Page<PatientResponseDto>> getAllPatients(@RequestBody PatientNameRequestDto dto) {
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize());
        return ResponseEntity.ok(patientService.getAllPatients(dto.getName(), pageable));
    }

    @PostMapping("/fetch")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST')")
    public ResponseEntity<ApiResponse<PatientResponseDto>> getPatientByParams(@RequestBody PatientRequestDto dto) {
        var data = patientService.getPatientByParams(dto);
        return ResponseEntity.ok(new ApiResponse<>("Patient fetched successfully", data));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST')")
    public ResponseEntity<ApiResponse<PatientResponseDto>> createPatient(
            @Valid @RequestBody PatientCreateRequestDto dto) {
        return ResponseEntity.ok(patientService.createPatient(dto));
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST')")
    public ResponseEntity<ApiResponse<PatientResponseDto>> updatePatient(@PathVariable Long id, @RequestBody PatientRequestDto dto) {
        return ResponseEntity.ok(patientService.updatePatient(id, dto));
    }

    @PutMapping("/{id}/archive")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST')")
    public ResponseEntity<ApiResponse<Void>> toggleArchive(
            @PathVariable Long id,
            @RequestParam boolean archived) {

        patientService.toggleArchivePatient(id, archived);
        String message = archived
                ? "Patient archived successfully"
                : "Patient restored successfully";

        return ResponseEntity.ok(new ApiResponse<>(message, null));
    }
}
