package com.kjm.toothlinedental.controller;

import com.kjm.toothlinedental.common.ApiResponse;
import com.kjm.toothlinedental.dto.service.ServiceResponseDto;
import com.kjm.toothlinedental.mapper.ServiceMapper;
import com.kjm.toothlinedental.repository.ServiceRepository;
import com.kjm.toothlinedental.service.ProcedureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/services")
public class PublicServiceController {

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    public PublicServiceController (ServiceRepository serviceRepository, ServiceMapper serviceMapper) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<ServiceResponseDto>>> getAllServicesPublic() {
        var data = serviceRepository.findAll().stream()
                .map(serviceMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>("Services fetched successfully", data));
    }
}
