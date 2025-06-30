package com.kjm.toothlinedental.controller;

import com.kjm.toothlinedental.dto.AppointmentRequestDto;
import com.kjm.toothlinedental.model.Appointment;
import com.kjm.toothlinedental.model.Patient;
import com.kjm.toothlinedental.repository.AppointmentRepository;
import com.kjm.toothlinedental.repository.PatientRepository;
import com.kjm.toothlinedental.repository.ServiceRepository;
import com.kjm.toothlinedental.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Appointment createAppointment(@RequestBody AppointmentRequestDto dto) {
        return appointmentService.createAppointment(dto); // no dentistId
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}