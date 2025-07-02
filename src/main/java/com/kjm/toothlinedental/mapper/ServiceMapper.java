package com.kjm.toothlinedental.mapper;

import com.kjm.toothlinedental.dto.ServiceResponseDto;
import com.kjm.toothlinedental.model.Service;
import org.springframework.stereotype.Component;

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
