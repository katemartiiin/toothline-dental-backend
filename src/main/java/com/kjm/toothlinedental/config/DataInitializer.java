package com.kjm.toothlinedental.config;

import com.kjm.toothlinedental.model.Role;
import com.kjm.toothlinedental.model.User;
import com.kjm.toothlinedental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String defaultEmail = "admin@toothline.com";
        String defaultDentistEmail = "melissa.c@toothline.com";

        // Admin
        if (userRepository.findByEmail(defaultEmail).isEmpty()) {
            User admin = new User();
            admin.setEmail(defaultEmail);
            admin.setPassword(passwordEncoder.encode("admin123")); // secure this in prod
            admin.setName("System Admin");
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
            System.out.println("System admin user created: " + defaultEmail);
        } else {
            System.out.println("System admin user already exists.");
        }

        // Dentist
        if (userRepository.findByEmail(defaultDentistEmail).isEmpty()) {
            User dentist = new User();
            dentist.setEmail(defaultDentistEmail);
            dentist.setPassword(passwordEncoder.encode("dentist123")); // secure this in prod
            dentist.setName("Dr. Melissa Chen");
            dentist.setRole(Role.DENTIST);

            userRepository.save(dentist);
            System.out.println("Default dentist created: " + defaultDentistEmail);
        } else {
            System.out.println("Default dentist already exists.");
        }
    }
}