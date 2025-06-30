package com.kjm.toothlinedental.controller;

import com.kjm.toothlinedental.dto.AppointmentRequestDto;
import com.kjm.toothlinedental.dto.AppointmentResponseDto;
import com.kjm.toothlinedental.model.Appointment;
import com.kjm.toothlinedental.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/appointments")
@CrossOrigin(origins = "*")
public class AdminAppointmentController {

    private final AppointmentService appointmentService;

    public AdminAppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('STAFF', 'DENTIST', 'ADMIN')")
    public Appointment createAppointmentAsAdmin(@RequestBody AppointmentRequestDto dto) {
        return appointmentService.createAppointment(dto);
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

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF', 'DENTIST', 'ADMIN')")
    public ResponseEntity<AppointmentResponseDto> getAppointmentById(@PathVariable Long id) {
        AppointmentResponseDto appointmentDto = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(appointmentDto);
    }
}