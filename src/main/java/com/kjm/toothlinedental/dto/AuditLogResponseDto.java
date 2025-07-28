package com.kjm.toothlinedental.dto;

public class AuditLogResponseDto {
    private Long id;
    private String details;
    private String performedBy;
    private String timestamp;

    public AuditLogResponseDto(Long id, String details, String performedBy, String timestamp) {
        this.id = id;
        this.details = details;
        this.performedBy = performedBy;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public String getPerformedBy() { return performedBy; }
    public void setPerformedBy(String performedBy) { this.performedBy = performedBy; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}