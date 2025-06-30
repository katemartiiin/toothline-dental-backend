package com.kjm.toothlinedental.controller;

import com.kjm.toothlinedental.dto.DashboardRequestDto;
import com.kjm.toothlinedental.model.Appointment;
import com.kjm.toothlinedental.repository.AppointmentRepository;
import com.kjm.toothlinedental.repository.PatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

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
