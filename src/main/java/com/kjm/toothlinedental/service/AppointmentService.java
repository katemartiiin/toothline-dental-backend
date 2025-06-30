package com.kjm.toothlinedental.service;

import com.kjm.toothlinedental.common.ApiResponse;
import com.kjm.toothlinedental.dto.appointment.AppointmentCreateRequestDto;
import com.kjm.toothlinedental.dto.appointment.AppointmentResponseDto;
import com.kjm.toothlinedental.dto.appointment.AppointmentUpdateRequestDto;
import com.kjm.toothlinedental.mapper.AppointmentMapper;
import com.kjm.toothlinedental.model.Appointment;
import com.kjm.toothlinedental.model.Patient;
import com.kjm.toothlinedental.model.Role;
import com.kjm.toothlinedental.model.User;
import com.kjm.toothlinedental.repository.AppointmentRepository;
import com.kjm.toothlinedental.repository.PatientRepository;
import com.kjm.toothlinedental.repository.ServiceRepository;
import com.kjm.toothlinedental.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              PatientRepository patientRepository,
                              ServiceRepository serviceRepository,
                              UserRepository userRepository,
                              AppointmentMapper appointmentMapper) {
        // Repository
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;

        // Mapper
        this.appointmentMapper = appointmentMapper;
    }
    // validate if selected User is a dentist
    public void assignDentist(Appointment appointment, User user) {
        if (!user.getRole().equals(Role.DENTIST)) {
            throw new IllegalArgumentException("Assigned user must be a DENTIST.");
        }
        appointment.setDentist(user);
    }

    /*
    * Create Appointment - used by Website Form and Admin System
    * For website - dentistId is not required
    * For admin system - dentistId can be assigned
    * */
    public ApiResponse<AppointmentResponseDto> createAppointment(AppointmentCreateRequestDto dto) {
        // Check if patient exists by email
        Patient patient = patientRepository.findByEmail(dto.getEmail())
                .orElseGet(() -> {
                    Patient newPatient = new Patient();
                    newPatient.setName(dto.getName());
                    newPatient.setEmail(dto.getEmail());
                    newPatient.setPhoneNumber(dto.getPhoneNumber());
                    return patientRepository.save(newPatient);
                });

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setService(serviceRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found")));
        appointment.setNotes(dto.getNotes());
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());

        // Set dentist if provided
        if (dto.getDentistId() != null) {
            User dentist = userRepository.findById(dto.getDentistId())
                    .orElseThrow(() -> new RuntimeException("Dentist not found"));
            if (!dentist.getRole().equals(Role.DENTIST)) {
                throw new IllegalArgumentException("Assigned user must be a dentist.");
            }
            appointment.setDentist(dentist);
            appointment.setStatus("CONFIRMED");
        } else {
            appointment.setStatus("PENDING");
        }

        Appointment saved = appointmentRepository.save(appointment);
        AppointmentResponseDto responseDto = appointmentMapper.toDto(saved);

        return new ApiResponse<>("Appointment created successfully", responseDto);
    }
    /*
    * Fetch Appointments
    * Params: dentistId, patientName, appointmentDate
    * Used for fetching appointments by parameters
    * */
    public List<AppointmentResponseDto> fetchAppointmentsBy(Long dentistId, String patientName, LocalDate appointmentDate) {
        List<Appointment> appointments = appointmentRepository.findFilteredAppointments(dentistId, patientName, appointmentDate);

        return appointments.stream()
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    /*
    * Fetch specific appointment
    * Params: appointmentId
    *
    * */
    public AppointmentResponseDto getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + id));

        return appointmentMapper.toDto(appointment);
    }

    public ApiResponse<AppointmentResponseDto> updateAppointment(Long id, AppointmentUpdateRequestDto dto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (dto.getAppointmentDate() != null) {
            appointment.setAppointmentDate(dto.getAppointmentDate());
        }

        if (dto.getAppointmentTime() != null) {
            appointment.setAppointmentTime(dto.getAppointmentTime());
        }

        if (dto.getStatus() != null) {
            appointment.setStatus(dto.getStatus());
        }

        if (dto.getDentistId() != null) {
            User dentist = userRepository.findById(dto.getDentistId())
                    .orElseThrow(() -> new RuntimeException("Dentist not found"));
            if (!dentist.getRole().equals(Role.DENTIST)) {
                throw new RuntimeException("Assigned user is not a dentist");
            }
            appointment.setDentist(dentist);
        } else {
            appointment.setDentist(null); // Clear assignment if null
        }

        Appointment saved = appointmentRepository.save(appointment);
        return new ApiResponse<>("Appointment updated successfully", appointmentMapper.toDto(saved));
    }

    public void toggleArchiveAppointment(Long id, boolean isArchive) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setArchived(isArchive);
        appointmentRepository.save(appointment);
    }

    public List<AppointmentResponseDto> getArchivedAppointments() {
        List<Appointment> archivedAppointments = appointmentRepository.findAllByArchivedTrue();
        return archivedAppointments.stream()
                .map(appointmentMapper::toDto)
                .toList();
    }
}
