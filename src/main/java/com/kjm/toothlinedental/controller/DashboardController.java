package com.kjm.toothlinedental.controller;

import java.util.List;
import java.time.LocalDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kjm.toothlinedental.model.Appointment;
import com.kjm.toothlinedental.dto.DashboardRequestDto;
import com.kjm.toothlinedental.repository.PatientRepository;
import com.kjm.toothlinedental.repository.appointment.AppointmentRepository;

@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardController {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    public DashboardController (AppointmentRepository appointmentRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    @GetMapping
    public ResponseEntity<DashboardRequestDto> getDashboardData() {
        LocalDate today = LocalDate.now();

        List<Appointment> todaysAppointments = appointmentRepository.findAllByAppointmentDateAndArchivedFalse(today);
        long patientCount = patientRepository.count();

        DashboardRequestDto dto = new DashboardRequestDto();
        dto.setAppointmentsToday(todaysAppointments);
        dto.setTotalPatients(patientCount);

        return ResponseEntity.ok(dto);
    }
}
