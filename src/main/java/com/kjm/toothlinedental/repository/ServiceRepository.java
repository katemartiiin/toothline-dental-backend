package com.kjm.toothlinedental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kjm.toothlinedental.model.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    // Optional: custom methods below
}