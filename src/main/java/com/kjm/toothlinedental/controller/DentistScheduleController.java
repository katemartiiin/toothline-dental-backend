package com.kjm.toothlinedental.controller;

import com.kjm.toothlinedental.common.ApiResponse;
import com.kjm.toothlinedental.dto.DashboardRequestDto;
import com.kjm.toothlinedental.dto.DentistScheduleFetchResponseDto;
import com.kjm.toothlinedental.dto.DentistScheduleRequestDto;
import com.kjm.toothlinedental.dto.DentistScheduleResponseDto;
import com.kjm.toothlinedental.model.Appointment;
import com.kjm.toothlinedental.model.DentistSchedule;
import com.kjm.toothlinedental.repository.DentistScheduleRepository;
import com.kjm.toothlinedental.service.DentistScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/admin/schedules")
@CrossOrigin(origins = "*")
public class DentistScheduleController {

    private final DentistScheduleService dentistScheduleService;

    public DentistScheduleController(DentistScheduleRepository dentistScheduleRepository,
                                     DentistScheduleService dentistScheduleService) {
        this.dentistScheduleService = dentistScheduleService;
    }

    @PostMapping("/fetch")
    @PreAuthorize("hasAnyRole('STAFF', 'DENTIST', 'ADMIN')")
    public ResponseEntity<DentistScheduleFetchResponseDto> fetchDentistSchedules(@RequestBody DentistScheduleRequestDto request) {
        List<DentistSchedule> dentistSchedules = dentistScheduleService.fetchDentistSchedulesBy(request.getDentistId(),
                request.getSchedDay());

        DentistScheduleFetchResponseDto dto = new DentistScheduleFetchResponseDto();
        dto.setDentistSchedules(dentistSchedules);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DentistScheduleResponseDto>> createDentistSchedule(@RequestBody DentistScheduleRequestDto dto) {
        return ResponseEntity.ok(dentistScheduleService.createDentistSchedule(dto)); // no dentistId
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasAnyRole('STAFF', 'DENTIST')")
    public ResponseEntity<ApiResponse<DentistScheduleResponseDto>> updateDentistSchedule(@PathVariable Long id, @RequestBody DentistScheduleRequestDto dto) {
        return ResponseEntity.ok(dentistScheduleService.updateDentistSchedule(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST')")
    public ResponseEntity<ApiResponse<Void>> deleteDentistSchedule(@PathVariable Long id) {
        dentistScheduleService.deleteDentistSchedule(id);
        return ResponseEntity.ok(new ApiResponse<>("Schedule deleted successfully", null));
    }
}