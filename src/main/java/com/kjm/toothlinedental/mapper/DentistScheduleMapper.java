package com.kjm.toothlinedental.mapper;

import org.springframework.stereotype.Component;

import com.kjm.toothlinedental.model.DentistSchedule;
import com.kjm.toothlinedental.dto.DentistScheduleResponseDto;

@Component
public class DentistScheduleMapper {

    public DentistScheduleResponseDto toDto(DentistSchedule schedule) {
        DentistScheduleResponseDto dto = new DentistScheduleResponseDto();
        dto.setDentistId(schedule.getDentist().getId());
        dto.setStatus(schedule.getStatus());
        dto.setSchedDay(schedule.getSchedDay());
        dto.setStartTime(schedule.getStartTime());
        dto.setEndTime(schedule.getEndTime());
        return dto;
    }
}
