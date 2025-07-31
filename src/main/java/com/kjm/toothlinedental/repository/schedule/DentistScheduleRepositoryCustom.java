package com.kjm.toothlinedental.repository.schedule;

import java.util.List;

import com.kjm.toothlinedental.model.DentistSchedule;

public interface DentistScheduleRepositoryCustom {
    List<DentistSchedule> findFilteredDentistSchedules(Long dentistId, String schedDay);
}
