package com.kjm.toothlinedental.controller;

import com.kjm.toothlinedental.dto.AppointmentRequestDto;
import com.kjm.toothlinedental.model.Appointment;
import com.kjm.toothlinedental.model.Patient;
import com.kjm.toothlinedental.repository.AppointmentRepository;
import com.kjm.toothlinedental.repository.PatientRepository;
import com.kjm.toothlinedental.repository.ServiceRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final ServiceRepository serviceRepository;

    public AppointmentController(AppointmentRepository appointmentRepository,
                                 PatientRepository patientRepository, ServiceRepository serviceRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.serviceRepository = serviceRepository;
    }

    @PostMapping
    public Appointment createAppointment(@RequestBody AppointmentRequestDto dto) {
        // Check if patient exists : patient email
        Patient patient = patientRepository.findByEmail(dto.getEmail())
                .orElseGet(() -> { // else create patient
                    Patient newPatient = new Patient();
                    newPatient.setName(dto.getName());
                    newPatient.setEmail(dto.getEmail());
                    newPatient.setPhoneNumber(dto.getPhoneNumber());
                    return patientRepository.save(newPatient);
                });
        // Create appointment
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        // set selected service id
        appointment.setService(serviceRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found")));
        appointment.setNotes(dto.getNotes());
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setStatus("PENDING"); // default appointment status
        return appointmentRepository.save(appointment);
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}