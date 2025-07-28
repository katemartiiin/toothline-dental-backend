package com.kjm.toothlinedental.controller;

import java.util.List;

import com.kjm.toothlinedental.dto.ServiceNameRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import com.kjm.toothlinedental.common.ApiResponse;
import com.kjm.toothlinedental.dto.ServiceRequestDto;
import com.kjm.toothlinedental.dto.ServiceResponseDto;
import com.kjm.toothlinedental.service.ProcedureService;

@RestController
@RequestMapping("/api/admin/services")
public class ServiceController {

    private final ProcedureService procedureService;

    public ServiceController (ProcedureService procedureService) {
        this.procedureService = procedureService;
    }

    @GetMapping("/{id}/view")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST')")
    public ResponseEntity<ApiResponse<ServiceResponseDto>> getServiceById(@PathVariable Long id) {
        var data = procedureService.getServiceById(id);
        return ResponseEntity.ok(new ApiResponse<>("Service fetched successfully", data));
    }

    @PostMapping("/fetch")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST')")
    public ResponseEntity<ApiResponse<List<ServiceResponseDto>>> getAllServices(@RequestBody ServiceNameRequestDto dto) {
        var data = procedureService.getAllServices(dto.getName());
        return ResponseEntity.ok(new ApiResponse<>("Services fetched successfully", data));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST')")
    public ResponseEntity<ApiResponse<ServiceResponseDto>> createService(@RequestBody ServiceRequestDto dto) {
        return ResponseEntity.ok(procedureService.createService(dto));
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST')")
    public ResponseEntity<ApiResponse<ServiceResponseDto>> updateService(@PathVariable Long id, @RequestBody ServiceRequestDto dto) {
        return ResponseEntity.ok(procedureService.updateService(id, dto));
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST')")
    public ResponseEntity<ApiResponse<Void>> deleteService(@PathVariable Long id) {
        procedureService.deleteService(id);
        return ResponseEntity.ok(new ApiResponse<>("Service deleted successfully", null));
    }
}
