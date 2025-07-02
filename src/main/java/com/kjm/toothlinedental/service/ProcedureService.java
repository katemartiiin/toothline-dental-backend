package com.kjm.toothlinedental.service;

import com.kjm.toothlinedental.common.ApiResponse;
import com.kjm.toothlinedental.dto.ServiceRequestDto;
import com.kjm.toothlinedental.dto.ServiceResponseDto;
import com.kjm.toothlinedental.mapper.ServiceMapper;
import com.kjm.toothlinedental.model.Service;
import com.kjm.toothlinedental.repository.ServiceRepository;

import java.util.List;

@org.springframework.stereotype.Service
public class ProcedureService {

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    public ProcedureService (ServiceRepository serviceRepository,
                             ServiceMapper serviceMapper
    ) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
    }

    /*
    * Fetch all services
    * */
    public List<ServiceResponseDto> getAllServices() {
        return serviceRepository.findAll()
                .stream()
                .map(serviceMapper::toDto)
                .toList();
    }

    /*
    * Fetch by Service Id
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
        return new ApiResponse<>("Service updated successfully", serviceMapper.toDto(saved));
    }

    public void deleteService(Long id) {
        if (!serviceRepository.existsById(id)) {
            throw new RuntimeException("Service not found");
        }

        serviceRepository.deleteById(id);
    }

}
