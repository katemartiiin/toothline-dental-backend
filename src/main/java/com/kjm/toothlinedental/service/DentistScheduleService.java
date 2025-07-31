package com.kjm.toothlinedental.service;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import com.kjm.toothlinedental.dto.schedule.DentistScheduleCreateRequestDto;
import com.kjm.toothlinedental.dto.schedule.DentistScheduleMyCreateRequestDto;
import com.kjm.toothlinedental.exception.ResourceNotFoundException;
import com.kjm.toothlinedental.model.ScheduleDay;
import com.kjm.toothlinedental.repository.schedule.DentistScheduleRepository;
import org.springframework.stereotype.Service;

import com.kjm.toothlinedental.repository.*;

import com.kjm.toothlinedental.model.User;
import com.kjm.toothlinedental.model.DentistSchedule;

import com.kjm.toothlinedental.common.ApiResponse;
import com.kjm.toothlinedental.common.SecurityUtils;

import com.kjm.toothlinedental.mapper.DentistScheduleMapper;

import com.kjm.toothlinedental.dto.schedule.DentistScheduleUpdateRequestDto;
import com.kjm.toothlinedental.dto.schedule.DentistScheduleResponseDto;

@Service
public class DentistScheduleService {

    private final UserRepository userRepository;
    private final DentistScheduleRepository dentistScheduleRepository;
    private final DentistScheduleMapper dentistScheduleMapper;
    private final AuditLogService auditLogService;

    public DentistScheduleService(UserRepository userRepository,
                                  DentistScheduleRepository dentistScheduleRepository,
                                  DentistScheduleMapper dentistScheduleMapper,
                                  AuditLogService auditLogService
    ) {
        // Repository
        this.userRepository = userRepository;
        this.dentistScheduleRepository = dentistScheduleRepository;
        this.dentistScheduleMapper = dentistScheduleMapper;
        this.auditLogService = auditLogService;
    }

    /*
     * Create Schedule - used by Staff
     * */
    public ApiResponse<DentistScheduleResponseDto> createDentistSchedule(DentistScheduleCreateRequestDto dto) {
        return saveSchedule(dto.getSchedDay(), dto.getStartTime(), dto.getEndTime(), dto.getStatus(), dto.getDentistId());
    }

    /*
     * Create Schedule - used by Dentist
     * */
    public ApiResponse<DentistScheduleResponseDto> createMyDentistSchedule(DentistScheduleMyCreateRequestDto dto, Long dentistId) {
        return saveSchedule(dto.getSchedDay(), dto.getStartTime(), dto.getEndTime(), dto.getStatus(), dentistId);
    }

    /*
     * Fetch Dentist Schedules
     * Params: dentistId, schedDay
     * Used for fetching dentist schedule by parameters
     * */
    public List<DentistSchedule> fetchDentistSchedulesBy(Long dentistId, String schedDay) {
        return dentistScheduleRepository.findFilteredDentistSchedules(dentistId, schedDay);
    }

    public Map<ScheduleDay, List<DentistScheduleResponseDto>> getGroupedScheduleWithAllDays(Long dentistId) {
        List<DentistSchedule> entities = dentistScheduleRepository.findByDentistId(dentistId);

        List<DentistScheduleResponseDto> dtos = entities.stream()
                .map(DentistScheduleResponseDto::new)
                .collect(Collectors.toList());

        Map<ScheduleDay, List<DentistScheduleResponseDto>> grouped = dtos.stream()
                .collect(Collectors.groupingBy(DentistScheduleResponseDto::getSchedDay));

        // Ensure all days are present
        Map<ScheduleDay, List<DentistScheduleResponseDto>> result = new LinkedHashMap<>();
        for (ScheduleDay day : ScheduleDay.values()) {
            result.put(day, grouped.getOrDefault(day, new ArrayList<>()));
        }

        return result;
    }

    /**
     * Update dentist schedule
     */
    public ApiResponse<DentistScheduleResponseDto> updateDentistSchedule(Long id, DentistScheduleUpdateRequestDto dto) {
        DentistSchedule schedule = dentistScheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dentist schedule not found with ID: " + id));

        if (dto.getSchedDay() != null) {
            schedule.setSchedDay(dto.getSchedDay());
        }

        if (dto.getStartTime() != null) {
            schedule.setStartTime(dto.getStartTime());
        }

        if (dto.getEndTime() != null) {
            schedule.setEndTime(dto.getEndTime());
        }

        if (dto.getStatus() != null) {
            schedule.setStatus(dto.getStatus());
        }

        DentistSchedule saved = dentistScheduleRepository.save(schedule);

        String performedBy = SecurityUtils.getCurrentUsername();
        auditLogService.logAction("UPDATE_SCHEDULE", performedBy, "Updated dentist schedule #" + id);

        return new ApiResponse<>("Dentist schedule #"+ id +" updated successfully", dentistScheduleMapper.toDto(saved));
    }

    /**
     * Delete dentist schedule
     */
    public void deleteDentistSchedule(Long id) {
        if (!dentistScheduleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Dentist schedule not found with ID: " + id);
        }

        dentistScheduleRepository.deleteById(id);

        String performedBy = SecurityUtils.getCurrentUsername();
        auditLogService.logAction("DELETE_SCHEDULE", performedBy, "Deleted dentist schedule #" + id);
    }

    /**
     * Common logic for creating dentist schedule
     */
    private ApiResponse<DentistScheduleResponseDto> saveSchedule(ScheduleDay schedDay, LocalTime startTime, LocalTime endTime, String status, Long dentistId) {
        DentistSchedule schedule = new DentistSchedule();
        User dentist = userRepository.findById(dentistId)
                .orElseThrow(() -> new ResourceNotFoundException("Dentist not found with ID: " + dentistId));
        schedule.setDentist(dentist);
        schedule.setSchedDay(schedDay);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        schedule.setStatus(status);

        DentistSchedule saved = dentistScheduleRepository.save(schedule);
        DentistScheduleResponseDto responseDto = dentistScheduleMapper.toDto(saved);

        String performedBy = SecurityUtils.getCurrentUsername();
        auditLogService.logAction("CREATE_SCHEDULE", performedBy, "Created dentist schedule #" + schedule.getId());

        return new ApiResponse<>("Dentist schedule #"+ schedule.getId() +" created successfully", responseDto);
    }
}
