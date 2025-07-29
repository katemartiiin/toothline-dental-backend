package com.kjm.toothlinedental.dto.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import com.kjm.toothlinedental.model.Role;

public class UserResponseDto {

    private Long id;
    private String email;
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
