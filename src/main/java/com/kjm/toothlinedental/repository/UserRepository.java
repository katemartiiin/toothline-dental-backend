package com.kjm.toothlinedental.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kjm.toothlinedental.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
