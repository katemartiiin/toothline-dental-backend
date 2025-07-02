package com.kjm.toothlinedental.dto;

import com.kjm.toothlinedental.model.Role;

public class AdminUpdateUserRequestDto {

    private Role role;                 // optional
    private Boolean resetPassword;

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Boolean getResetPassword() { return resetPassword; }
    public void setResetPassword(Boolean resetPassword) { this.resetPassword = resetPassword; }
}
