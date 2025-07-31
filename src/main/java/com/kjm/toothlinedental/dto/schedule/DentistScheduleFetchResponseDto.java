package com.kjm.toothlinedental.dto.schedule;

import com.kjm.toothlinedental.model.DentistSchedule;

import java.util.List;

public class DentistScheduleFetchResponseDto {
    private List<DentistSchedule> dentistSchedules;

    public List<DentistSchedule> getDentistSchedules() {
        return dentistSchedules;
    }

    public void setDentistSchedules(List<DentistSchedule> dentistSchedules) {
        this.dentistSchedules = dentistSchedules;
    }

}
