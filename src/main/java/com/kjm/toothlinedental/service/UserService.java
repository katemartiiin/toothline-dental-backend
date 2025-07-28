package com.kjm.toothlinedental.service;

import java.util.List;

import com.kjm.toothlinedental.model.Role;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kjm.toothlinedental.dto.*;
import com.kjm.toothlinedental.model.User;
import com.kjm.toothlinedental.mapper.UserMapper;

import com.kjm.toothlinedental.common.ApiResponse;
import com.kjm.toothlinedental.common.SecurityUtils;
import com.kjm.toothlinedental.common.PasswordGenerator;

import com.kjm.toothlinedental.repository.UserRepository;
import org.springframework.util.StringUtils;

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
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /*
    * Fetch users by role
    * */
    public List<UserResponseDto> getUsersByRole(String role) {
        List<User> users;

        if (!role.isEmpty()){
            Role roleEnum;
            try {
                roleEnum = Role.valueOf(role.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid role: " + role);
            }

            users = userRepository.findByRole(roleEnum);
        } else {
            users = userRepository.findAll();
        }

        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    /*
     * Fetch by userId
     * */
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }

    /*
     * Create User
     * */
    public ApiResponse<UserCreateResponseDto> createUser(UserRequestDto dto) {

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

    public ApiResponse<UserProfileResponseDto> updateUserProfile(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (dto.getName() != null) {
            user.setName(dto.getName());
        }

        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }

        if (dto.getCurrentPassword() != null && !dto.getCurrentPassword().isEmpty()) {
            if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
                throw new RuntimeException("Current password is incorrect");
            }
            if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
                throw new RuntimeException("Passwords do not match");
            }
            user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        }

        User saved = userRepository.save(user);

        String performedBy = SecurityUtils.getCurrentUsername();
        auditLogService.logAction("UPDATE_PROFILE", performedBy, "Updated profile for user #" + id);

        return new ApiResponse<>("Profile updated successfully", userMapper.profileToDto(saved));
    }

    public ApiResponse<AdminUpdateUserResponseDto> updateUserAsAdmin(Long userId, AdminUpdateUserRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

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

        return new ApiResponse<>("User updated successfully", responseDto);
    }
}
