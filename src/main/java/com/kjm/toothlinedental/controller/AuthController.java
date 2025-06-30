package com.kjm.toothlinedental.controller;

import com.kjm.toothlinedental.dto.LoginRequestDto;
import com.kjm.toothlinedental.dto.LoginResponseDto;
import com.kjm.toothlinedental.model.User;
import com.kjm.toothlinedental.repository.UserRepository;
import com.kjm.toothlinedental.service.JwtService;
import com.kjm.toothlinedental.service.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final TokenBlacklistService blacklistService;
    private final JwtService jwtService;

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenBlacklistService blacklistService, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.blacklistService = blacklistService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        User user = userOpt.get();
        String token = jwtService.generateToken(user);

        LoginResponseDto response = new LoginResponseDto();
        response.setToken(token);
        response.setName(user.getName());
        response.setRole(user.getRole());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            if (!jwtService.isTokenValid(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }

            blacklistService.blacklist(token);
            return ResponseEntity.ok("Logged out successfully");
        }

        return ResponseEntity.badRequest().body("Missing token");
    }
}