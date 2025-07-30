package com.kjm.toothlinedental.dto;

public class AuditLogFetchRequestDto {

    private String performedBy;
    private String date;
    private String category;

    public String getPerformedBy() { return performedBy; }
    public void setPerformedBy(String performedBy) { this.performedBy = performedBy; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
