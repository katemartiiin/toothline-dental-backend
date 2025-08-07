package com.kjm.toothlinedental.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kjm.toothlinedental.model.Service;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    Page<Service> findAllByOrderByCreatedAtAsc(Pageable pageable);

    Page<Service> findByNameContainingIgnoreCase(String name, Pageable pageable);
}