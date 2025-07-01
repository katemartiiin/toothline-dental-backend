package com.kjm.toothlinedental.repository;

import com.kjm.toothlinedental.model.DentistSchedule;

import java.time.LocalDate;
import java.util.List;

public interface DentistScheduleRepositoryCustom {
    List<DentistSchedule> findFilteredDentistSchedules(Long dentistId, String schedDay);
}
