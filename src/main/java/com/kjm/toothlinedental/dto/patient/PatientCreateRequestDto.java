package com.kjm.toothlinedental.dto.patient;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class PatientCreateRequestDto {

    @NotBlank(message = "Patient name is required.")
    private String name;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email format is invalid.")
    private String email;

    @NotBlank(message = "Phone number is required.")
    private String phoneNumber;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

}
