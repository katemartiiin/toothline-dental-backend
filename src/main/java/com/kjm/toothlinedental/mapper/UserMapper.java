package com.kjm.toothlinedental.mapper;

import org.springframework.stereotype.Component;

import com.kjm.toothlinedental.model.User;
import com.kjm.toothlinedental.dto.UserResponseDto;
import com.kjm.toothlinedental.dto.UserProfileResponseDto;

@Component
public class UserMapper {

    public UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        copyCommonFields(user, dto);
        dto.setRole(user.getRole());
        return dto;
    }

    public UserProfileResponseDto profileToDto(User user) {
        UserProfileResponseDto dto = new UserProfileResponseDto();
        copyCommonFields(user, dto);
        return dto;
    }

    // Common fields
    private void copyCommonFields(User user, Object targetDto) {
        if (targetDto instanceof UserResponseDto dto) {
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
        } else if (targetDto instanceof UserProfileResponseDto dto) {
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
        }
    }
}