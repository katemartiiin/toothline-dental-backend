package com.kjm.toothlinedental.controller;

import java.util.List;
import java.util.Map;

import com.kjm.toothlinedental.dto.schedule.DentistScheduleCreateRequestDto;
import com.kjm.toothlinedental.model.ScheduleDay;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import com.kjm.toothlinedental.common.ApiResponse;
import com.kjm.toothlinedental.service.DentistScheduleService;
import com.kjm.toothlinedental.dto.schedule.DentistScheduleUpdateRequestDto;
import com.kjm.toothlinedental.dto.schedule.DentistScheduleResponseDto;
import com.kjm.toothlinedental.repository.DentistScheduleRepository;

@RestController
@RequestMapping("/api/admin/schedules")
@CrossOrigin(origins = "*")
public class DentistScheduleController {

    private final DentistScheduleService dentistScheduleService;

    public DentistScheduleController(DentistScheduleRepository dentistScheduleRepository,
                                     DentistScheduleService dentistScheduleService) {
        this.dentistScheduleService = dentistScheduleService;
    }

    @GetMapping("/{id}/fetch")
    @PreAuthorize("hasAnyRole('STAFF', 'DENTIST', 'ADMIN')")
    public ResponseEntity<Map<ScheduleDay, List<DentistScheduleResponseDto>>> getSchedule(@PathVariable Long id) {
        return ResponseEntity.ok(dentistScheduleService.getGroupedScheduleWithAllDays(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST', 'STAFF')")
    public ResponseEntity<ApiResponse<DentistScheduleResponseDto>> createDentistSchedule(
            @Valid @RequestBody DentistScheduleCreateRequestDto dto) {
        return ResponseEntity.ok(dentistScheduleService.createDentistSchedule(dto)); // no dentistId
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST', 'STAFF')")
    public ResponseEntity<ApiResponse<DentistScheduleResponseDto>> updateDentistSchedule(@PathVariable Long id, @RequestBody DentistScheduleUpdateRequestDto dto) {
        return ResponseEntity.ok(dentistScheduleService.updateDentistSchedule(id, dto));
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST', 'STAFF')")
    public ResponseEntity<ApiResponse<Void>> deleteDentistSchedule(@PathVariable Long id) {
        dentistScheduleService.deleteDentistSchedule(id);
        return ResponseEntity.ok(new ApiResponse<>("Schedule deleted successfully", null));
    }
}