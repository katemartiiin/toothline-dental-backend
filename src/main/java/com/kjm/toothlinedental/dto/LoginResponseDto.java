package com.kjm.toothlinedental.dto;

import com.kjm.toothlinedental.model.Role;

public class LoginResponseDto {
    private String token;
    private String name;
    private Role role;

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
}
