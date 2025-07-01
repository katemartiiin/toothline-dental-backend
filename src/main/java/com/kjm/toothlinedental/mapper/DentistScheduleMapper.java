package com.kjm.toothlinedental.mapper;

import com.kjm.toothlinedental.dto.DentistScheduleResponseDto;
import com.kjm.toothlinedental.model.DentistSchedule;
import org.springframework.stereotype.Component;

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
