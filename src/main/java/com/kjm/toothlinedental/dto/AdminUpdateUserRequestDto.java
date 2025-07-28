package com.kjm.toothlinedental.dto;

import com.kjm.toothlinedental.model.Role;

public class AdminUpdateUserRequestDto {

    private String role;                 // optional
    private boolean resetPassword;
    private boolean locked;

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isResetPassword() { return resetPassword; }
    public void setResetPassword(boolean resetPassword) { this.resetPassword = resetPassword; }

    public boolean isLocked() { return locked; }
    public void setLocked(boolean locked) { this.locked = locked; }
}
