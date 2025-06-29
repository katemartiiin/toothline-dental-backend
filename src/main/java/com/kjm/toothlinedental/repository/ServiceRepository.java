package com.kjm.toothlinedental.repository;

import com.kjm.toothlinedental.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    // Optional: custom methods below
}