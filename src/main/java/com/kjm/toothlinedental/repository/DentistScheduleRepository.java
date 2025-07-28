package com.kjm.toothlinedental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kjm.toothlinedental.model.DentistSchedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DentistScheduleRepository extends JpaRepository<DentistSchedule, Long>, DentistScheduleRepositoryCustom {

    @Query("SELECT s FROM DentistSchedule s WHERE s.dentist.id = :dentistId")
    List<DentistSchedule> findByDentistId(@Param("dentistId") Long dentistId);
}
