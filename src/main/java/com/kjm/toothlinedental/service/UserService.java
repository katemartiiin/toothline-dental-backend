package com.kjm.toothlinedental.service;

import java.util.List;

import com.kjm.toothlinedental.dto.user.*;
import com.kjm.toothlinedental.exception.BadRequestException;
import com.kjm.toothlinedental.exception.ResourceNotFoundException;
import com.kjm.toothlinedental.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kjm.toothlinedental.model.User;
import com.kjm.toothlinedental.mapper.UserMapper;

import com.kjm.toothlinedental.common.ApiResponse;
import com.kjm.toothlinedental.common.SecurityUtils;

import com.kjm.toothlinedental.repository.UserRepository;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuditLogService auditLogService;

    public UserService(PasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       UserMapper userMapper,
                       AuditLogService auditLogService
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.auditLogService = auditLogService;
    }

    /*
     * Fetch all users
     * */
    public Page<UserResponseDto> getAllUsers(Pageable pageable) {
        return userRepository.findAllByOrderByIdAsc(pageable)
                .map(userMapper::toDto);
    }

    /*
    * Fetch users by role
    * */
    public Page<UserResponseDto> getUsersByRole(String role, Pageable pageable) {
        Page<User> users;

        if (!role.isEmpty()){
            Role roleEnum;
            try {
                roleEnum = Role.valueOf(role.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Invalid role: " + role);
            }

            users = userRepository.findByRole(roleEnum, pageable);
        } else {
            users = userRepository.findAllByOrderByIdAsc(pageable);
        }

        return users.map(userMapper::toDto);
    }

    /*
     * Fetch by userId
     * */
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        return userMapper.toDto(user);
    }

    /*
     * Create User
     * */
    public ApiResponse<UserCreateResponseDto> createUser(UserCreateRequestDto dto) {

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(Role.valueOf(dto.getRole()));
        // Generate default password
//        String rawPassword = PasswordGenerator.generateRandomPassword();
        String rawPassword = "toothline123";
        user.setPassword(passwordEncoder.encode(rawPassword));

        User saved = userRepository.save(user);
        UserResponseDto userDto = userMapper.toDto(saved);

        UserCreateResponseDto responseDto = new UserCreateResponseDto(userDto, rawPassword);

        String performedBy = SecurityUtils.getCurrentUsername();
        auditLogService.logAction("CREATE_USER", performedBy, "Created user #" + user.getId());

        return new ApiResponse<>("User created successfully", responseDto);
    }

    public ApiResponse<UserProfileResponseDto> updateUserProfile(Long id, UserUpdateRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        if (dto.getName() != null) {
            user.setName(dto.getName());
        }

        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }

        if (dto.getCurrentPassword() != null && !dto.getCurrentPassword().isEmpty()) {
            if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
                throw new BadRequestException("Current password is incorrect");
            }
            if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
                throw new BadRequestException("Passwords do not match");
            }
            user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        }

        User saved = userRepository.save(user);

        String performedBy = SecurityUtils.getCurrentUsername();
        auditLogService.logAction("UPDATE_PROFILE", performedBy, "Updated user profile" + id);

        return new ApiResponse<>("Profile updated successfully", userMapper.profileToDto(saved));
    }

    public ApiResponse<AdminUpdateUserResponseDto> updateUserAsAdmin(Long userId, AdminUpdateUserRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        if (dto.getRole() != null && !dto.getRole().isEmpty()) {
            user.setRole(Role.valueOf(dto.getRole()));
        }

        String rawPassword = null;
        if (dto.isResetPassword()) {
            rawPassword = "toothline123";
//            rawPassword = PasswordGenerator.generateRandomPassword();
            user.setPassword(passwordEncoder.encode(rawPassword));
        }

        if (dto.isLocked()) {
            user.setLocked(true);
        }

        User updated = userRepository.save(user);
        UserResponseDto userDto = userMapper.toDto(updated);

        AdminUpdateUserResponseDto responseDto = new AdminUpdateUserResponseDto(userDto, rawPassword);

        String performedBy = SecurityUtils.getCurrentUsername();
        auditLogService.logAction("UPDATE_USER_AS_ADMIN", performedBy, "Updated user #" + user.getId());

        return new ApiResponse<>("User #" + user.getId() + " updated successfully", responseDto);
    }
}
