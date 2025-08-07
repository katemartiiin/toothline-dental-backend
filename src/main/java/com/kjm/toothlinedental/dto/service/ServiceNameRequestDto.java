package com.kjm.toothlinedental.dto.service;

public class ServiceNameRequestDto {

    private String name;
    private int page = 0;
    private int size = 10;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }
}
