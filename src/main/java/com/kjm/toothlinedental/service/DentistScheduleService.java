package com.kjm.toothlinedental.service;

import com.kjm.toothlinedental.common.ApiResponse;
import com.kjm.toothlinedental.common.SecurityUtils;
import com.kjm.toothlinedental.dto.DentistScheduleResponseDto;
import com.kjm.toothlinedental.dto.DentistScheduleRequestDto;
import com.kjm.toothlinedental.mapper.DentistScheduleMapper;
import com.kjm.toothlinedental.model.DentistSchedule;
import com.kjm.toothlinedental.model.User;
import com.kjm.toothlinedental.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * Create Schedule - used by Dentist
     * */
    public ApiResponse<DentistScheduleResponseDto> createDentistSchedule(DentistScheduleRequestDto dto) {

        DentistSchedule schedule = new DentistSchedule();
        User dentist = userRepository.findById(dto.getDentistId())
                .orElseThrow(() -> new RuntimeException("Dentist not found"));
        schedule.setDentist(dentist);
        schedule.setSchedDay(dto.getSchedDay());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());
        schedule.setStatus(dto.getStatus());

        DentistSchedule saved = dentistScheduleRepository.save(schedule);
        DentistScheduleResponseDto responseDto = dentistScheduleMapper.toDto(saved);

        String performedBy = SecurityUtils.getCurrentUsername();
        auditLogService.logAction("CREATE_SCHEDULE", performedBy, "Created dentist schedule #" + schedule.getId());

        return new ApiResponse<>("Schedule created successfully", responseDto);
    }
    /*
     * Fetch Dentist Schedules
     * Params: dentistId, schedDay
     * Used for fetching dentist schedule by parameters
     * */
    public List<DentistSchedule> fetchDentistSchedulesBy(Long dentistId, String schedDay) {
        return dentistScheduleRepository.findFilteredDentistSchedules(dentistId, schedDay);
    }

    public ApiResponse<DentistScheduleResponseDto> updateDentistSchedule(Long id, DentistScheduleRequestDto dto) {
        DentistSchedule schedule = dentistScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

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

        return new ApiResponse<>("Schedule updated successfully", dentistScheduleMapper.toDto(saved));
    }

    public void deleteDentistSchedule(Long id) {
        if (!dentistScheduleRepository.existsById(id)) {
            throw new RuntimeException("Schedule not found");
        }

        dentistScheduleRepository.deleteById(id);

        String performedBy = SecurityUtils.getCurrentUsername();
        auditLogService.logAction("DELETE_SCHEDULE", performedBy, "Deleted dentist schedule #" + id);
    }
}
