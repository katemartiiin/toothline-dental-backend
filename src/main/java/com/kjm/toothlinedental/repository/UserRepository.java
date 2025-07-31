package com.kjm.toothlinedental.repository;

import java.util.List;
import java.util.Optional;

import com.kjm.toothlinedental.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kjm.toothlinedental.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Page<User> findAllByOrderByIdAsc(Pageable pageable);

    Page<User> findByRole(Role role, Pageable pageable);
}
