package com.kjm.toothlinedental.dto.service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ServiceCreateRequestDto {

    @NotBlank(message = "Service name is required.")
    private String name;
    private String description;

    @Min(value = 1, message = "Duration must be at least 1 minute.")
    private int durationMinutes;

    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be zero or positive")
    private Double price;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
