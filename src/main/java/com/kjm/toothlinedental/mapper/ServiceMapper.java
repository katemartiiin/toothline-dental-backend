package com.kjm.toothlinedental.mapper;

import org.springframework.stereotype.Component;

import com.kjm.toothlinedental.model.Service;
import com.kjm.toothlinedental.dto.service.ServiceResponseDto;

@Component
public class ServiceMapper {

    public ServiceResponseDto toDto(Service service) {
        ServiceResponseDto dto = new ServiceResponseDto();
        dto.setId(service.getId());
        dto.setName(service.getName());
        dto.setDescription(service.getDescription());
        dto.setDurationMinutes(service.getDurationMinutes());
        dto.setPrice(service.getPrice());
        return dto;
    }
}
