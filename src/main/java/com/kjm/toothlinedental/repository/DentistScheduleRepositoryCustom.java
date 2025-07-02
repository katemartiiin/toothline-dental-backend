package com.kjm.toothlinedental.repository;

import java.util.List;

import com.kjm.toothlinedental.model.DentistSchedule;

public interface DentistScheduleRepositoryCustom {
    List<DentistSchedule> findFilteredDentistSchedules(Long dentistId, String schedDay);
}
