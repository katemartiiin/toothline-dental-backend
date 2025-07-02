package com.kjm.toothlinedental.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import com.kjm.toothlinedental.common.ApiResponse;
import com.kjm.toothlinedental.service.AppointmentService;
import com.kjm.toothlinedental.dto.appointment.AppointmentRequestDto;
import com.kjm.toothlinedental.dto.appointment.AppointmentResponseDto;
import com.kjm.toothlinedental.dto.appointment.AppointmentCreateRequestDto;
import com.kjm.toothlinedental.dto.appointment.AppointmentUpdateRequestDto;

@RestController
@RequestMapping("/api/admin/appointments")
@CrossOrigin(origins = "*")
public class AdminAppointmentController {

    private final AppointmentService appointmentService;

    public AdminAppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'DENTIST')")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> createAppointmentAsAdmin(@RequestBody AppointmentCreateRequestDto dto) {
        return ResponseEntity.ok(appointmentService.createAppointment(dto));
    }

    @PostMapping("/fetch")
    @PreAuthorize("hasAnyRole('STAFF', 'DENTIST', 'ADMIN')")
    public ResponseEntity<List<AppointmentResponseDto>> fetchAppointments(@RequestBody AppointmentRequestDto request) {
        List<AppointmentResponseDto> results = appointmentService.fetchAppointmentsBy(
                request.getDentistId(),
                request.getPatientName(),
                request.getAppointmentDate()
        );
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}/view")
    @PreAuthorize("hasAnyRole('STAFF', 'DENTIST', 'ADMIN')")
    public ResponseEntity<AppointmentResponseDto> getAppointmentById(@PathVariable Long id) {
        AppointmentResponseDto appointmentDto = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(appointmentDto);
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasAnyRole('STAFF', 'DENTIST')")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> updateAppointment(@PathVariable Long id, @RequestBody AppointmentUpdateRequestDto dto) {
        return ResponseEntity.ok(appointmentService.updateAppointment(id, dto));
    }

    @GetMapping("/archived")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST')")
    public ResponseEntity<ApiResponse<List<AppointmentResponseDto>>> getArchivedAppointments() {
        List<AppointmentResponseDto> data = appointmentService.getArchivedAppointments();
        return ResponseEntity.ok(new ApiResponse<>("Archived appointments fetched successfully", data));
    }

    @PutMapping("/{id}/archive")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST')")
    public ResponseEntity<ApiResponse<Void>> toggleArchive(
            @PathVariable Long id,
            @RequestParam boolean archived) {

        appointmentService.toggleArchiveAppointment(id, archived);
        String message = archived
                ? "Appointment archived successfully"
                : "Appointment restored successfully";

        return ResponseEntity.ok(new ApiResponse<>(message, null));
    }
}