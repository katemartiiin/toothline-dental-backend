package com.kjm.toothlinedental.dto;

import java.time.LocalTime;

public class DentistScheduleResponseDto {

    private Long dentistId;
    private String schedDay;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;

    public Long getDentistId() { return dentistId; }
    public void setDentistId(Long dentistId) { this.dentistId = dentistId; }

    public String getSchedDay() { return schedDay; }
    public void setSchedDay(String schedDay) { this.schedDay = schedDay; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime schedTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
