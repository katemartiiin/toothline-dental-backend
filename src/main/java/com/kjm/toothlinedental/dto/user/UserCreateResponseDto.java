package com.kjm.toothlinedental.dto.user;

public class UserCreateResponseDto {

    private UserResponseDto user;
    private String defaultPassword;

    public UserCreateResponseDto(UserResponseDto user, String defaultPassword) {
        this.user = user;
        this.defaultPassword = defaultPassword;
    }

    public UserResponseDto getUser() { return user; }
    public void setUser(UserResponseDto user) { this.user = user; }

    public String getDefaultPassword() { return defaultPassword; }
    public void setDefaultPassword(String defaultPassword) { this.defaultPassword = defaultPassword; }
}
