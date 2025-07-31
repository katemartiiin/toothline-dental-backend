package com.kjm.toothlinedental.controller;

import java.util.List;

import com.kjm.toothlinedental.dto.service.ServiceCreateRequestDto;
import com.kjm.toothlinedental.dto.service.ServiceNameRequestDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import com.kjm.toothlinedental.common.ApiResponse;
import com.kjm.toothlinedental.dto.service.ServiceUpdateRequestDto;
import com.kjm.toothlinedental.dto.service.ServiceResponseDto;
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
    public ResponseEntity<Page<ServiceResponseDto>> getAllServices(@RequestBody ServiceNameRequestDto dto) {
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize());
        return ResponseEntity.ok(procedureService.getAllServices(dto.getName(), pageable));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST')")
    public ResponseEntity<ApiResponse<ServiceResponseDto>> createService(
            @Valid @RequestBody ServiceCreateRequestDto dto) {
        return ResponseEntity.ok(procedureService.createService(dto));
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST')")
    public ResponseEntity<ApiResponse<ServiceResponseDto>> updateService(@PathVariable Long id, @RequestBody ServiceUpdateRequestDto dto) {
        return ResponseEntity.ok(procedureService.updateService(id, dto));
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTIST')")
    public ResponseEntity<ApiResponse<Void>> deleteService(@PathVariable Long id) {
        procedureService.deleteService(id);
        return ResponseEntity.ok(new ApiResponse<>("Service #"+id+" deleted successfully", null));
    }
}
