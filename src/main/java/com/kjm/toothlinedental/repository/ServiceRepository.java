package com.kjm.toothlinedental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kjm.toothlinedental.model.Service;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findAllByOrderByCreatedAtAsc();

    List<Service> findByNameContainingIgnoreCase(String name);
}