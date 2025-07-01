package com.kjm.toothlinedental.repository;

import com.kjm.toothlinedental.model.DentistSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DentistScheduleRepository extends JpaRepository<DentistSchedule, Long>, DentistScheduleRepositoryCustom {

}
