package com.kjm.toothlinedental.dto.schedule;

import com.kjm.toothlinedental.model.DentistSchedule;
import com.kjm.toothlinedental.model.ScheduleDay;

import java.time.LocalTime;

public class DentistScheduleResponseDto {

    private Long id;
    private Long dentistId;
    private ScheduleDay schedDay;

    private LocalTime startTime;
    private LocalTime endTime;
    private String status;

    public DentistScheduleResponseDto(DentistSchedule s) {
        this.id = s.getId();
        this.dentistId = s.getDentist().getId();
        this.startTime = s.getStartTime();
        this.endTime = s.getEndTime();
        this.schedDay = s.getSchedDay();
        this.status = s.getStatus();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getDentistId() { return dentistId; }
    public void setDentistId(Long dentistId) { this.dentistId = dentistId; }

    public ScheduleDay getSchedDay() { return schedDay; }
    public void setSchedDay(ScheduleDay schedDay) { this.schedDay = schedDay; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime schedTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
