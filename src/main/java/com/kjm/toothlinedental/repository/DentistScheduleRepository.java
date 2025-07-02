package com.kjm.toothlinedental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kjm.toothlinedental.model.DentistSchedule;

public interface DentistScheduleRepository extends JpaRepository<DentistSchedule, Long>, DentistScheduleRepositoryCustom {

}
