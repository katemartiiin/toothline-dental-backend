package com.kjm.toothlinedental.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.kjm.toothlinedental.model.ScheduleDay;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import com.kjm.toothlinedental.common.ApiResponse;
import com.kjm.toothlinedental.model.DentistSchedule;
import com.kjm.toothlinedental.service.DentistScheduleService;
import com.kjm.toothlinedental.dto.DentistScheduleRequestDto;
import com.kjm.toothlinedental.dto.DentistScheduleResponseDto;
import com.kjm.toothlinedental.dto.DentistScheduleFetchResponseDto;
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
    public ResponseEntity<ApiResponse<DentistScheduleResponseDto>> createDentistSchedule(@RequestBody DentistScheduleRequestDto dto) {
        return ResponseEntity.ok(dentistScheduleService.createDentistSchedule(dto)); // no dentistId
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST', 'STAFF')")
    public ResponseEntity<ApiResponse<DentistScheduleResponseDto>> updateDentistSchedule(@PathVariable Long id, @RequestBody DentistScheduleRequestDto dto) {
        return ResponseEntity.ok(dentistScheduleService.updateDentistSchedule(id, dto));
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST', 'STAFF')")
    public ResponseEntity<ApiResponse<Void>> deleteDentistSchedule(@PathVariable Long id) {
        dentistScheduleService.deleteDentistSchedule(id);
        return ResponseEntity.ok(new ApiResponse<>("Schedule deleted successfully", null));
    }
}