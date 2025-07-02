package com.kjm.toothlinedental.controller;

import com.kjm.toothlinedental.common.ApiResponse;
import com.kjm.toothlinedental.dto.ServiceRequestDto;
import com.kjm.toothlinedental.dto.ServiceResponseDto;
import com.kjm.toothlinedental.service.ProcedureService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/services")
public class ServiceController {

    private final ProcedureService procedureService;

    public ServiceController (ProcedureService procedureService) {
        this.procedureService = procedureService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST')")
    public ResponseEntity<ApiResponse<List<ServiceResponseDto>>> getAllServices() {
        var data = procedureService.getAllServices();
        return ResponseEntity.ok(new ApiResponse<>("Services fetched successfully", data));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST')")
    public ResponseEntity<ApiResponse<ServiceResponseDto>> getServiceById(@PathVariable Long id) {
        var data = procedureService.getServiceById(id);
        return ResponseEntity.ok(new ApiResponse<>("Service fetched successfully", data));
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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST')")
    public ResponseEntity<ApiResponse<Void>> deleteService(@PathVariable Long id) {
        procedureService.deleteService(id);
        return ResponseEntity.ok(new ApiResponse<>("Service deleted successfully", null));
    }
}
