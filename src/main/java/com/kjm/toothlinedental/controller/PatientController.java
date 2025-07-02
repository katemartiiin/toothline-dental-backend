package com.kjm.toothlinedental.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.kjm.toothlinedental.common.ApiResponse;
import com.kjm.toothlinedental.dto.PatientRequestDto;
import com.kjm.toothlinedental.dto.PatientResponseDto;
import com.kjm.toothlinedental.service.PatientService;

@RestController
@RequestMapping("/api/admin/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController (PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST')")
    public ResponseEntity<ApiResponse<List<PatientResponseDto>>> getAllPatients() {
        var data = patientService.getAllPatients();
        return ResponseEntity.ok(new ApiResponse<>("Patients fetched successfully", data));
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

    @PostMapping("/fetch")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST')")
    public ResponseEntity<ApiResponse<PatientResponseDto>> getPatientByParams(@RequestBody PatientRequestDto dto) {
        var data = patientService.getPatientByParams(dto);
        return ResponseEntity.ok(new ApiResponse<>("Patient fetched successfully", data));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST')")
    public ResponseEntity<ApiResponse<PatientResponseDto>> createPatient(@RequestBody PatientRequestDto dto) {
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
