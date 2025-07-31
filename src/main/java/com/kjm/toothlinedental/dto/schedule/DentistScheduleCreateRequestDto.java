package com.kjm.toothlinedental.dto.schedule;

import com.kjm.toothlinedental.model.ScheduleDay;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public class DentistScheduleCreateRequestDto {

    @NotNull(message = "Please select a dentist.")
    private Long dentistId;

    @NotNull(message = "Please select a day.")
    private ScheduleDay schedDay;

    @NotNull(message = "Start time is required.")
    private LocalTime startTime;

    @NotNull(message = "End time is required.")
    private LocalTime endTime;

    @NotNull(message = "Status is required.")
    private String status;

    public Long getDentistId() { return dentistId; }
    public void setDentistId(Long dentistId) { this.dentistId = dentistId; }

    public ScheduleDay getSchedDay() { return schedDay; }
    public void setSchedDay(ScheduleDay schedDay) { this.schedDay = schedDay; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
