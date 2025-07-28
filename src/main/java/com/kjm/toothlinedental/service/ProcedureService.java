package com.kjm.toothlinedental.service;

import java.util.List;

import com.kjm.toothlinedental.model.Service;

import com.kjm.toothlinedental.mapper.ServiceMapper;

import com.kjm.toothlinedental.common.ApiResponse;
import com.kjm.toothlinedental.common.SecurityUtils;

import com.kjm.toothlinedental.dto.ServiceRequestDto;
import com.kjm.toothlinedental.dto.ServiceResponseDto;

import com.kjm.toothlinedental.repository.ServiceRepository;

@org.springframework.stereotype.Service
public class ProcedureService {

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;
    private final AuditLogService auditLogService;

    public ProcedureService (ServiceRepository serviceRepository,
                             ServiceMapper serviceMapper,
                             AuditLogService auditLogService
    ) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
        this.auditLogService = auditLogService;
    }

    /*
    * Fetch all services
    * */
    public List<ServiceResponseDto> getAllServices(String name) {
        List<Service> services = (name == null || name.trim().isEmpty())
                ? serviceRepository.findAll()
                : serviceRepository.findByNameContainingIgnoreCase(name);

        return services.stream()
                .map(serviceMapper::toDto)
                .toList();
    }

    /*
    * Fetch by serviceId
    * */
    public ServiceResponseDto getServiceById(Long id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        return serviceMapper.toDto(service);
    }

    /*
     * Create Service
     * */
    public ApiResponse<ServiceResponseDto> createService(ServiceRequestDto dto) {

        Service service = new Service();
        service.setName(dto.getName());
        service.setDescription(dto.getDescription());
        service.setDurationMinutes(dto.getDurationMinutes());
        service.setPrice(dto.getPrice());

        Service saved = serviceRepository.save(service);
        ServiceResponseDto responseDto = serviceMapper.toDto(saved);

        String performedBy = SecurityUtils.getCurrentUsername();
        auditLogService.logAction("CREATE_SERVICE", performedBy, "Created service #" + service.getId());

        return new ApiResponse<>("Service created successfully", responseDto);
    }

    public ApiResponse<ServiceResponseDto> updateService(Long id, ServiceRequestDto dto) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        if (dto.getName() != null) {
            service.setName(dto.getName());
        }

        if (dto.getDescription() != null) {
            service.setDescription(dto.getDescription());
        }

        if (dto.getDurationMinutes() != service.getDurationMinutes()) {
            service.setDurationMinutes(dto.getDurationMinutes());
        }

        if (dto.getPrice() != service.getPrice()) {
            service.setPrice(dto.getPrice());
        }

        Service saved = serviceRepository.save(service);

        String performedBy = SecurityUtils.getCurrentUsername();
        auditLogService.logAction("UPDATE_SERVICE", performedBy, "Updated service #" + id);

        return new ApiResponse<>("Service updated successfully", serviceMapper.toDto(saved));
    }

    public void deleteService(Long id) {
        if (!serviceRepository.existsById(id)) {
            throw new RuntimeException("Service not found");
        }

        serviceRepository.deleteById(id);

        String performedBy = SecurityUtils.getCurrentUsername();
        auditLogService.logAction("DELETE_SERVICE", performedBy, "Deleted service #" + id);
    }

}
