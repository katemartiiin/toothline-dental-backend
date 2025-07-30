package com.kjm.toothlinedental.service;

import java.util.List;
import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Collectors;

import com.kjm.toothlinedental.exception.BadRequestException;
import com.kjm.toothlinedental.exception.ResourceNotFoundException;
import com.kjm.toothlinedental.model.*;

import com.kjm.toothlinedental.common.ApiResponse;
import com.kjm.toothlinedental.common.SecurityUtils;
import com.kjm.toothlinedental.mapper.AppointmentMapper;

import com.kjm.toothlinedental.repository.UserRepository;
import com.kjm.toothlinedental.repository.PatientRepository;
import com.kjm.toothlinedental.repository.ServiceRepository;
import com.kjm.toothlinedental.repository.AppointmentRepository;

import com.kjm.toothlinedental.dto.appointment.AppointmentResponseDto;
import com.kjm.toothlinedental.dto.appointment.AppointmentUpdateRequestDto;
import com.kjm.toothlinedental.dto.appointment.AppointmentCreateRequestDto;

@org.springframework.stereotype.Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final AppointmentMapper appointmentMapper;
    private final AuditLogService auditLogService;
    private final JwtService jwtService;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              PatientRepository patientRepository,
                              ServiceRepository serviceRepository,
                              UserRepository userRepository,
                              AppointmentMapper appointmentMapper,
                              AuditLogService auditLogService,
                              JwtService jwtService) {
        // Repository
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;

        // Mapper
        this.appointmentMapper = appointmentMapper;

        // Service
        this.auditLogService = auditLogService;
        this.jwtService = jwtService;
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
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with ID: " + dto.getServiceId())));
        appointment.setNotes(dto.getNotes());
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());

        // Set dentist if provided
        if (dto.getDentistId() != null) {
            User dentist = userRepository.findById(dto.getDentistId())
                    .orElseThrow(() -> new ResourceNotFoundException("Dentist not found with ID: " + dto.getDentistId()));
            if (!dentist.getRole().equals(Role.DENTIST)) {
                throw new BadRequestException("Assigned user must be a dentist.");
            }
            appointment.setDentist(dentist);
            appointment.setStatus(AppointmentStatus.CONFIRMED);
        } else {
            appointment.setStatus(AppointmentStatus.PENDING);
        }

        Appointment saved = appointmentRepository.save(appointment);
        AppointmentResponseDto responseDto = appointmentMapper.toDto(saved);

        return new ApiResponse<>("Appointment created successfully.", responseDto);
    }
    /*
    * Fetch Appointments
    * Params: dentistId, patientName, appointmentDate
    * Used for fetching appointments by parameters
    * */
    public List<AppointmentResponseDto> fetchAppointmentsBy(Long serviceId, String patientName, LocalDate appointmentDate, String token) {
        Long dentistId = null;
        String role = jwtService.getRole(token);

        if (Objects.equals(role, "DENTIST")) {
            dentistId = Long.valueOf(jwtService.getUserId(token));
        }

        List<Appointment> appointments = appointmentRepository.findFilteredAppointments(serviceId, patientName, appointmentDate, dentistId);

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
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with ID: " + id));

        return appointmentMapper.toDto(appointment);
    }

    /*
    * Update appointment
    * */
    public ApiResponse<AppointmentResponseDto> updateAppointment(Long id, AppointmentUpdateRequestDto dto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with ID: " + id));

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
                    .orElseThrow(() -> new ResourceNotFoundException("Dentist not found with ID: " + dto.getDentistId()));
            if (!dentist.getRole().equals(Role.DENTIST)) {
                throw new BadRequestException("Assigned user is not a dentist.");
            }
            appointment.setDentist(dentist);
        }

        if (dto.getServiceId() != null) {
            Service service = serviceRepository.findById(dto.getServiceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Service not found with ID: " + dto.getServiceId()));
            appointment.setService(service);
        }

        if (!dto.getNotes().equals(appointment.getNotes())) {
            appointment.setNotes(dto.getNotes());
        }

        Appointment saved = appointmentRepository.save(appointment);

        String performedBy = SecurityUtils.getCurrentUsername();
        auditLogService.logAction("UPDATE_APPOINTMENT", performedBy, "Updated details for appointment #" + id);

        return new ApiResponse<>("Appointment #" + id + " updated successfully.", appointmentMapper.toDto(saved));
    }

    /*
     * Update appointment status
     * */
    public ApiResponse<AppointmentResponseDto> updateAppointmentStatus(Long id, String status) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with ID: " + id));

        if (status != null) {
            appointment.setStatus(AppointmentStatus.valueOf(status));
        }

        Appointment saved = appointmentRepository.save(appointment);

        String performedBy = SecurityUtils.getCurrentUsername();
        auditLogService.logAction("UPDATE_APPOINTMENT", performedBy, "Updated status for appointment #" + id);

        return new ApiResponse<>("Appointment #" + id +" is now " + saved.getStatus() + ".", appointmentMapper.toDto(saved));
    }

    public void toggleArchiveAppointment(Long id, boolean isArchive) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with ID: " + id));

        appointment.setArchived(isArchive);
        appointmentRepository.save(appointment);

        String performedBy = SecurityUtils.getCurrentUsername();
        String auditMessage = (isArchive ? "Archived" : "Restored") + " appointment #" + id;
        auditLogService.logAction("ARCHIVE_APPOINTMENT", performedBy, auditMessage);
    }

    public List<AppointmentResponseDto> getArchivedAppointments() {
        List<Appointment> archivedAppointments = appointmentRepository.findAllByArchivedTrue();
        return archivedAppointments.stream()
                .map(appointmentMapper::toDto)
                .toList();
    }
}
