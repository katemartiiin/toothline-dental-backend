package com.kjm.toothlinedental;

import java.util.Optional;
import java.time.LocalTime;
import java.time.LocalDate;

import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.kjm.toothlinedental.common.ApiResponse;
import com.kjm.toothlinedental.dto.appointment.*;

import com.kjm.toothlinedental.model.Patient;
import com.kjm.toothlinedental.model.Service;
import com.kjm.toothlinedental.model.Appointment;

import com.kjm.toothlinedental.repository.*;

import com.kjm.toothlinedental.mapper.AppointmentMapper;

import com.kjm.toothlinedental.service.AppointmentService;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @Test
    void createAppointment_shouldCreateNewAppointment() {
        // Arrange
        Service service = new Service();
        service.setId(1L);

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setEmail("john@example.com");

        Appointment appointment = new Appointment();
        appointment.setId(1L);

        AppointmentCreateRequestDto request = new AppointmentCreateRequestDto();
        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setPhoneNumber("1234567890");
        request.setServiceId(service.getId());
        request.setAppointmentDate(LocalDate.now());
        request.setAppointmentTime(LocalTime.of(10, 0));
        request.setNotes("First appointment");

        AppointmentResponseDto responseDto = new AppointmentResponseDto();
        responseDto.setId(1L);

        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));
        when(patientRepository.findByEmail("john@example.com")).thenReturn(Optional.of(patient));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);
        when(appointmentMapper.toDto(appointment)).thenReturn(responseDto);

        // Act
        ApiResponse<AppointmentResponseDto> response = appointmentService.createAppointment(request);

        // Assert
        assertNotNull(response.getData());
        assertEquals(1L, response.getData().getId());
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }
}
