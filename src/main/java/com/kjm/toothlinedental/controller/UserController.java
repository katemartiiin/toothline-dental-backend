package com.kjm.toothlinedental.controller;

import java.util.List;

import com.kjm.toothlinedental.dto.user.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.kjm.toothlinedental.model.User;
import com.kjm.toothlinedental.common.ApiResponse;
import com.kjm.toothlinedental.service.UserService;
import com.kjm.toothlinedental.repository.UserRepository;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService,
                          UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    // Get all users - admin only
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponseDto>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    // Get user by ID - admin only
    @GetMapping("/{id}/view")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Get current user's profile
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponseDto> getCurrentUserProfile(
            @AuthenticationPrincipal String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(userService.getUserById(user.getId()));
    }

    // Get users by role
    @GetMapping("/role")
    @PreAuthorize("hasAnyRole('STAFF', 'DENTIST', 'ADMIN')")
    public ResponseEntity<Page<UserResponseDto>> getUsersByRole(
            @RequestParam String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.getUsersByRole(role, pageable));
    }

    // Create user - admin only
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserCreateResponseDto>> createUser(
            @Valid @RequestBody UserCreateRequestDto dto) {
        return ResponseEntity.ok(userService.createUser(dto));
    }

    // Update own profile (including password)
    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponseDto>> updateUserProfile(
            @AuthenticationPrincipal String email,
            @RequestBody UserUpdateRequestDto dto) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(userService.updateUserProfile(user.getId(), dto));
    }

    // Admin update: change role / reset password
    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AdminUpdateUserResponseDto>> updateUserAsAdmin(
            @PathVariable Long id,
            @RequestBody AdminUpdateUserRequestDto dto) {
        return ResponseEntity.ok(userService.updateUserAsAdmin(id, dto));
    }
}