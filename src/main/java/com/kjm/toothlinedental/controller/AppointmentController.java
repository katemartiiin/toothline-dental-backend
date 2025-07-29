package com.kjm.toothlinedental.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kjm.toothlinedental.common.ApiResponse;
import com.kjm.toothlinedental.model.Appointment;
import com.kjm.toothlinedental.service.AppointmentService;
import com.kjm.toothlinedental.repository.AppointmentRepository;
import com.kjm.toothlinedental.dto.appointment.AppointmentResponseDto;
import com.kjm.toothlinedental.dto.appointment.AppointmentCreateRequestDto;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentRepository appointmentRepository,
                                 AppointmentService appointmentService) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> createAppointment(
            @Valid @RequestBody AppointmentCreateRequestDto dto) {
        return ResponseEntity.ok(appointmentService.createAppointment(dto)); // no dentistId
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}